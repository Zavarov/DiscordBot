/*
 * Copyright (C) 2017 u/Zavarov
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

package vartas.discord.blanc.command.mod;

import net.dv8tion.jda.core.entities.Message;
import vartas.discord.bot.api.communicator.CommunicatorInterface;
import vartas.discord.bot.command.entity._ast.ASTEntityType;

import java.util.List;

/**
 * This command allows to blacklist certain words. Any message that contains the
 * word will be deleted by the bot.
 */
public class FilterCommand extends FilterCommandTOP {
    public FilterCommand(Message source, CommunicatorInterface communicator, List<ASTEntityType> parameters) throws IllegalArgumentException, IllegalStateException {
        super(source, communicator, parameters);
    }

    /**
     * Changes the blacklisted words as specified by the parameters and stores the changes in the configuration file.
     */
    @Override
    public void run(){
        String expression = expressionSymbol.resolve();

        if(config.isFiltered(expression)){
            config.addToFilter(expression);
            communicator.send(channel, "Messages containing "+expression+" will now be removed when possible.");
        }else{
            config.removeFromFilter(expression);
            communicator.send(channel, "Messages containing "+expression+" will no longer be removed.");
        }
    }
}
