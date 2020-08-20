/*
 * Copyright (c) 2018 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.command.debug;

import io.gomint.command.Command;
import io.gomint.command.CommandOutput;
import io.gomint.command.CommandSender;
import io.gomint.command.annotation.Description;
import io.gomint.command.annotation.Name;
import io.gomint.server.entity.EntityPlayer;

import java.util.Map;

@Name("start")
@Description( "Kick a player from the server." )
public class StartGameCommand extends Command {

    @Override
    public CommandOutput execute(CommandSender commandSender, String alias, Map<String, Object> arguments) {
        EntityPlayer target = (EntityPlayer) commandSender;
        target.getConnection().sendWorldInitialization(1337);
        return null;
    }

}
