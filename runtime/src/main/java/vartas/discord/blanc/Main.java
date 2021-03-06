/*
 * Copyright (c) 2020 Zavarov
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package vartas.discord.blanc;

import de.se_rwth.commons.logging.Log;
import vartas.discord.blanc.callable.MontiCoreCommandParser;
import vartas.discord.blanc.io.$json.JSONCredentials;
import vartas.discord.blanc.io.Credentials;
import vartas.discord.blanc.monticore.MontiCoreCommandBuilder;
import vartas.discord.blanc.parser.JDATypeResolver;
import vartas.discord.blanc.parser.Parser;
import vartas.reddit.JSONClient;
import vartas.reddit.PushshiftClient;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.nio.file.Paths;

public class Main {
    //TODO Integrate into the architecture
    public static vartas.reddit.Client REDDIT_CLIENT;
    @Nonnull
    public static final Client CLIENT = new Client();
    @Nonnull
    private static final Parser PARSER = new MontiCoreCommandParser();

    static{
        //The application would terminate on an invalid command, for example
        Log.enableFailQuick(false);
    }

    public static void main(String[] args) throws IOException {
        Credentials credentials = JSONCredentials.fromJson(new Credentials(), Paths.get("credentials.json"));
        ShardLoader shardLoader = createShardLoader(credentials);

        REDDIT_CLIENT = new PushshiftClient(
                credentials.getRedditAccount(),
                credentials.getVersion(),
                credentials.getRedditId(),
                credentials.getRedditSecret()
        );
        REDDIT_CLIENT = new JSONClient(REDDIT_CLIENT, Paths.get("reddit"));

        for(int i = 0 ; i < credentials.getShardCount() ; ++i)
            CLIENT.addShards(loadShard(shardLoader, i));
    }

    private static ShardLoader createShardLoader(Credentials credentials){
        return new JDAShardLoader(credentials, (shard, jda) -> new MontiCoreCommandBuilder((guild, textChannel) -> new JDATypeResolver(shard, jda, guild, textChannel), shard, PARSER, credentials.getGlobalPrefix()));
    }

    private static Shard loadShard(ShardLoader shardLoader, int shardId){
        return shardLoader.load(shardId);
    }
}
