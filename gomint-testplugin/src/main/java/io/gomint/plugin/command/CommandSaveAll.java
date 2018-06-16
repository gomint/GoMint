/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.plugin.command;

import io.gomint.command.Command;
import io.gomint.command.CommandOutput;
import io.gomint.command.annotation.Description;
import io.gomint.command.annotation.Name;
import io.gomint.entity.EntityPlayer;
import io.gomint.world.World;

import java.util.Map;

/**
 * @author geNAZt
 * @version 1.0
 */
@Name( "save-all" )
@Description( "Persist all chunks to disk" )
public class CommandSaveAll extends Command {

    @Override
    public CommandOutput execute( EntityPlayer player, String alias, Map<String, Object> arguments ) {
        World world = player.getWorld();
        world.save();

        return new CommandOutput().success( "World has been saved" );
    }

}
