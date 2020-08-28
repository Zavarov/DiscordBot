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

package vartas.discord.blanc.mock;

import vartas.discord.blanc.*;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class SelfMemberMock extends SelfMember {
    @Nonnull
    @Override
    public List<Permission> getPermissions(@Nonnull TextChannel textChannel) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<String> retrieveNickname() {
        return Optional.empty();
    }

    @Override
    public List<Role> retrieveRoles() {
        return Collections.emptyList();
    }

    @Override
    public void modifyRoles(Collection<Role> rolesToAdd, Collection<Role> rolesToRemove) {
        throw new UnsupportedOperationException();
    }
    @Override
    public String getAsMention() {
        throw new UnsupportedOperationException();
    }

    @Nonnull
    @Override
    public MessageEmbed toMessageEmbed() {
        throw new UnsupportedOperationException();
    }
}
