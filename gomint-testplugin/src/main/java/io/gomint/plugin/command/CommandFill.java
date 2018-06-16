/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.plugin.command;

import io.gomint.GoMint;
import io.gomint.command.Command;
import io.gomint.command.CommandOutput;
import io.gomint.command.CommandSender;
import io.gomint.command.PlayerCommandSender;
import io.gomint.command.annotation.Description;
import io.gomint.command.annotation.Name;
import io.gomint.command.annotation.Overload;
import io.gomint.command.annotation.Parameter;
import io.gomint.command.validator.BlockPositionValidator;
import io.gomint.command.validator.StringValidator;
import io.gomint.entity.EntityPlayer;
import io.gomint.math.BlockPosition;
import io.gomint.world.World;
import io.gomint.world.block.Block;

import java.util.Map;

/**
 * @author geNAZt
 * @version 1.0
 */
@Name( "fill" )
@Description( "Fill a area with given block" )
@Overload( {
    @Parameter( name = "start", validator = BlockPositionValidator.class ),
    @Parameter( name = "end", validator = BlockPositionValidator.class ),
    @Parameter( name = "block", validator = StringValidator.class, arguments = { ".*" } )
} )
public class CommandFill extends Command {

    @Override
    public CommandOutput execute( CommandSender commandSender, String alias, Map<String, Object> arguments ) {
        if ( commandSender instanceof PlayerCommandSender ) {
            EntityPlayer player = (EntityPlayer) commandSender;
            World world = player.getWorld();

            String blockSelected = (String) arguments.get( "block" );

            try {
                Class<? extends Block> blockClass = (Class<? extends Block>) CommandFill.class.getClassLoader().loadClass( "io.gomint.world.block.Block" + blockSelected );
                BlockPosition start = (BlockPosition) arguments.get( "start" );
                BlockPosition end = (BlockPosition) arguments.get( "end" );

                for ( int x = start.getX(); x < end.getX(); x++ ) {
                    for ( int y = start.getY(); y < end.getY(); y++ ) {
                        for ( int z = start.getZ(); z < end.getZ(); z++ ) {
                            world.getBlockAt( x, y, z ).setType( blockClass );
                        }
                    }
                }
            } catch ( ClassNotFoundException e ) {
                return new CommandOutput().fail( "Could not find block '%%s'", blockSelected );
            }

            return new CommandOutput().success( "Filled selected area with block '%%s'", blockSelected );
        }

        return new CommandOutput().fail( "Command can only be executed as player" );
    }

}
