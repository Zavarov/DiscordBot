package vartas.discord.blanc.command.reddit;

import vartas.chart.Interval.Interval;
import java.time.LocalDate.LocalDate;

group reddit {
    command subreddit{
             class : SubredditChartCommand
         parameter : String subreddit, LocalDate from, LocalDate to, String type
              rank : REDDIT
    }
    command snowflake{
             class : SnowflakeChartCommand
         parameter : String subreddit, LocalDate from, LocalDate to, Interval interval
              rank : Reddit
    }
    command table{
             class : SnowflakeTableCommand
         parameter : String subreddit, LocalDate from, LocalDate to, String type
              rank : REDDIT
    }
    command markdown{
             class : MarkdownTableCommand
         parameter : String subreddit, LocalDate from, LocalDate to, String type
              rank : REDDIT
    }
}