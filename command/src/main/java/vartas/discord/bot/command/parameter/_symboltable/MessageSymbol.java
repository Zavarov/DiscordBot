package vartas.discord.bot.command.parameter._symboltable;

import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.exceptions.ErrorResponseException;
import net.dv8tion.jda.core.requests.ErrorResponse;
import vartas.discord.bot.command.entity._ast.ASTMessageType;

import java.util.Optional;

import static com.google.common.base.Preconditions.checkNotNull;

/*
 * Copyright (C) 2019 Zavarov
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
public class MessageSymbol extends MessageSymbolTOP{
    protected ASTMessageType ast;

    public MessageSymbol(String name) {
        super(name);
    }

    public void setValue(ASTMessageType ast){
        this.ast = ast;
    }

    public ASTMessageType getValue(){
        return ast;
    }

    public Optional<Message> resolve(Message context){
        checkNotNull(context);
        checkNotNull(context.getTextChannel());

        try {
            return Optional.of(context.getTextChannel().getMessageById(ast.getId().getValue()).complete());
        }catch(ErrorResponseException e){
            //The message id was invalid
            if(e.getErrorResponse() == ErrorResponse.UNKNOWN_MESSAGE)
                return Optional.empty();
            throw e;
        }
    }
}