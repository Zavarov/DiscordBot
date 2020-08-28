package vartas.discord.blanc.visitor;

import org.apache.http.client.HttpResponseException;
import org.atteo.evo.inflector.English;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vartas.discord.blanc.*;
import vartas.discord.blanc.io.json.JSONCredentials;
import vartas.discord.blanc.json.JSONGuild;
import vartas.reddit.Submission;
import vartas.reddit.Subreddit;
import vartas.reddit.UnsuccessfulRequestException;

import javax.annotation.Nonnull;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * This visitor class is responsible for requesting the latest submissions
 * from all registered subreddits in the internal structure.
 * <br>
 * In order to detect new submissions, an internal {@link Instant}
 * of the most recent {@link Submission} is kept. During each cycle,
 * all submissions past this date are considered to be new. In addition,
 * the {@link Instant} of the latest {@link Submission} becomes the new
 * date for the succeeding cycle.
 */
@Nonnull
public class RedditVisitor implements ArchitectureVisitor {
    /**
     * This class's {@link Logger}, logging the individual phases of the request.
     */
    @Nonnull
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    /**
     * The hook point for receiving new {@link Submission submissions}.
     */
    @Nonnull
    private final vartas.reddit.Client redditClient;
    /**
     * The creation time of the most recent {@link Submission}.
     */
    @Nonnull
    private Instant inclusiveFrom = Instant.now();
    /**
     * The time when the current cycle was started.
     */
    @Nonnull
    private Instant exclusiveTo = inclusiveFrom;

    /**
     * Indicates that the guild file was modified by the visitor and the corresponding JSON
     * file needs to be updated.
     */
    private boolean requiresUpdate;

    /**
     * Initializes the visitor.
     * @param redditClient the hook point for receiving new {@link Submission submissions}
     */
    public RedditVisitor
            (
                    @Nonnull vartas.reddit.Client redditClient
            )
    {
        this.redditClient = redditClient;
    }

    /**
     * Update {@link #exclusiveTo} with the current time, and {@link #inclusiveFrom} with the end of the last cycle.
     * @param shard the current {@link Shard}.
     */
    @Override
    public void visit(@Nonnull ShardTOP shard){
        log.debug("Visiting shard {}.", shard.getId());

        //Keep the dates synchronized between multiple shards.
        if(shard.getId() == 0) {
            //Take the timestamp from the last cycle
            inclusiveFrom = exclusiveTo;
            //Submissions need to be at least one minute old
            exclusiveTo = Instant.now().minus(1, ChronoUnit.MINUTES);
        }
    }

    /**
     * Log when entering a guild.
     * @param guild the current {@link Guild}.
     */
    @Override
    public void visit(@Nonnull GuildTOP guild){
        log.debug("Visiting guild {}", guild.getName());
        requiresUpdate = false;
    }

    /**
     * Request the latest submissions from all registered subreddits in this channel and submit them.
     * In case one of the requests failed, either due to an error on either clients, unregister the subreddit.
     * @param textChannel the current {@link TextChannel}.
     */
    @Override
    public void visit(@Nonnull TextChannel textChannel){
        log.debug("Visiting text channel {}", textChannel.getName());
        for(String subreddit : textChannel.getSubreddits())
            request(subreddit, textChannel::send, textChannel::removeSubreddits);
        for(Map.Entry<String, Webhook> webhook : textChannel.asMapWebhooks().entrySet())
            request(webhook.getKey(), webhook.getValue()::send, textChannel::invalidateWebhooks);
    }

    @Override
    public void endVisit(@Nonnull GuildTOP guild){
        if(requiresUpdate)
            Shard.write(JSONGuild.of(guild), JSONCredentials.CREDENTIALS.getGuildDirectory().resolve(guild.getId()+".gld"));
    }

    private void request
    (
            @Nonnull String name,
            @Nonnull BiConsumer<Subreddit, Submission> onSuccess,
            @Nonnull Consumer<String> onFailure
    )
    {
        try {
            Subreddit subreddit = redditClient.getSubreddits(name);
            List<Submission> submissions = subreddit.getSubmissions(inclusiveFrom, exclusiveTo);

            log.debug("{} new {} between {} and {}",
                    submissions.size(),
                    English.plural("submission", submissions.size()),
                    inclusiveFrom,
                    exclusiveTo
            );

            //Post the individual submissions
            for (Submission submission : submissions)
                onSuccess.accept(subreddit, submission);
            //Reddit Exceptions
        } catch( UnsuccessfulRequestException e) {
            log.warn(Errors.UNSUCCESSFUL_REDDIT_REQUEST.toString(), e);
        } catch(vartas.reddit.TimeoutException e) {
            log.warn(Errors.REDDIT_TIMEOUT.toString(), e);
            //Caused by either Reddit or Discord
        } catch(HttpResponseException | PermissionException | WebhookException e) {
            e.printStackTrace();
            log.error(Errors.REFUSED_REDDIT_REQUEST.toString(), e);
            onFailure.accept(name);
            requiresUpdate = true;
        } catch(Exception e) {
            log.warn(Errors.UNKNOWN_RESPONSE.toString(), e.toString());
        }
    }
}