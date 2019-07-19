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

package vartas.discord.blanc.command.base;

import net.dv8tion.jda.core.entities.Message;
import vartas.discord.bot.api.communicator.CommunicatorInterface;
import vartas.discord.bot.command.entity._ast.ASTEntityType;

import java.util.List;

/**
 * This command posts the invitation link for the bot.
 */
public class InviteCommand extends InviteCommandTOP{
    public InviteCommand(Message source, CommunicatorInterface communicator, List<ASTEntityType> parameters) throws IllegalArgumentException, IllegalStateException {
        super(source, communicator, parameters);
    }

    /**
     * Retrieves the link from the config file and sends it.
     */
    @Override
    public void run() {
        StringBuilder builder = new StringBuilder();

        builder.append("Use this link if you want to add this bot to your server:\n");
        builder.append(String.format("https://discordapp.com/oauth2/authorize?client_id=%s&scope=bot", communicator.jda().getSelfUser().getId()));
        communicator.send(channel, builder.toString());
    }
}
