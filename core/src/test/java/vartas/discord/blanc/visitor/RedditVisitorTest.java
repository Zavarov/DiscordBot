package vartas.discord.blanc.visitor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import vartas.discord.blanc.*;
import vartas.discord.blanc.factory.GuildFactory;
import vartas.discord.blanc.factory.ShardFactory;
import vartas.discord.blanc.factory.TextChannelFactory;
import vartas.discord.blanc.factory.WebhookFactory;
import vartas.discord.blanc.mock.*;
import vartas.reddit.Submission;
import vartas.reddit.factory.SubmissionFactory;
import vartas.reddit.factory.SubredditFactory;

import java.io.IOException;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

public class RedditVisitorTest extends AbstractTest {
    Shard shard;
    Guild guild;
    TextChannelMock textChannel;

    ClientMock redditHook;
    RedditVisitor redditVisitor;
    SubredditMock subreddit;
    Submission submission;
    WebhookMock webhook;
    @BeforeEach
    public void setUp() throws IOException {
        initRedditHook();
        initDiscordHook();
        redditVisitor = new RedditVisitor(redditHook);
    }

    private void initRedditHook(){
        subreddit = (SubredditMock) SubredditFactory.create(
                SubredditMock::new,
                "subreddit",
                "description",
                //It's over 9000 :O
                9001,
                "id",
                Instant.now()
        );

        //TODO From JSON
        submission = SubmissionFactory.create(
                SubmissionMock::new,
                "author",
                "title",
                0,
                false,
                false,
                "id",
                Instant.now()
        );

        redditHook = new ClientMock();
        redditHook.putSubreddits(subreddit.getName(), subreddit);

        subreddit.submissions.add(submission);
    }

    private void initDiscordHook() {
        webhook = (WebhookMock) WebhookFactory.create(WebhookMock::new, 12345L,"subreddit");

        textChannel = (TextChannelMock) TextChannelFactory.create(TextChannelMock::new, 2, "TextChannel");
        textChannel.addSubreddits(subreddit.getName());
        textChannel.putWebhooks("subreddit", webhook);

        guild = GuildFactory.create(GuildMock::new, new SelfMemberMock(), 1, "Guild");
        guild.putChannels(textChannel.getId(), textChannel);

        shard = ShardFactory.create(0, new SelfUserMock());
        shard.putGuilds(guild.getId(), guild);

    }

    @Test
    public void testRedditTimeoutException(){
        subreddit.action = SubredditMock.ACTION.TIMEOUT_EXCEPTION;

        shard.accept(redditVisitor);

        assertThat(textChannel.getSubreddits()).containsExactly(subreddit.getName());
        assertThat(textChannel.valuesWebhooks()).containsExactly(webhook);
        assertThat(textChannel.sent).isEmpty();
        assertThat(webhook.sent).isEmpty();
    }

    @Test
    public void testRedditHttpException(){
        subreddit.action = SubredditMock.ACTION.HTTP_EXCEPTION;

        shard.accept(redditVisitor);

        assertThat(textChannel.isEmptySubreddits()).isTrue();
        assertThat(textChannel.valuesWebhooks()).isEmpty();
        assertThat(textChannel.sent).isEmpty();
        assertThat(webhook.sent).isEmpty();
    }

    @Test
    public void testRedditUnsuccessfulException(){
        subreddit.action = SubredditMock.ACTION.UNSUCCESSFUL_EXCEPTION;

        shard.accept(redditVisitor);

        assertThat(textChannel.getSubreddits()).containsExactly(subreddit.getName());
        assertThat(textChannel.valuesWebhooks()).containsExactly(webhook);
        assertThat(textChannel.sent).isEmpty();
        assertThat(webhook.sent).isEmpty();
    }

    @Test
    public void testUnknownException(){
        subreddit.action = SubredditMock.ACTION.UNKNOWN_EXCEPTION;

        shard.accept(redditVisitor);

        assertThat(textChannel.getSubreddits()).containsExactly(subreddit.getName());
        assertThat(textChannel.valuesWebhooks()).containsExactly(webhook);
        assertThat(textChannel.sent).isEmpty();
        assertThat(webhook.sent).isEmpty();
    }

    @Test
    public void testSuccess(){
        shard.accept(redditVisitor);

        assertThat(textChannel.sent).isNotEmpty();
        assertThat(webhook.sent).isNotEmpty();
    }
}
