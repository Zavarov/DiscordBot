/*
 * Copyright (c) 2019 Zavarov
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

package vartas.discord.blanc.command.developer;

import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Message;
import vartas.discord.bot.api.communicator.CommunicatorInterface;
import vartas.discord.bot.command.entity._ast.ASTEntityType;

import java.util.List;

/**
 * This command changes the online status of the bot.
 */
public class OnlineStatusCommand extends OnlineStatusCommandTOP{
    public OnlineStatusCommand(Message source, CommunicatorInterface communicator, List<ASTEntityType> parameters) throws IllegalArgumentException, IllegalStateException {
        super(source, communicator, parameters);
    }

    /**
     * Retrieves the status message and applies this to the most recent JDA instance.
     * This will also update all other instances.
     */
    @Override
    public void run(){
        OnlineStatus status = statusSymbol.resolve();
        communicator.jda().getPresence().setStatus(status);
    }
}
