
package zav.discord.blanc.parser._factory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Supplier;
import zav.discord.blanc.ConfigurationModule;
import zav.discord.blanc.Guild;
import zav.discord.blanc.Member;
import zav.discord.blanc.Message;
import zav.discord.blanc.Role;
import zav.discord.blanc.TextChannel;
import zav.discord.blanc.User;
import zav.discord.blanc.parser.*;



 public  class ParserFactory   {
     public  static  Parser create( Supplier<? extends Parser> _factoryParser){

        Parser _ParserInstance = _factoryParser.get();
        return _ParserInstance;
    }

}