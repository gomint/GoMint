/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.plugin.command;

import io.gomint.command.Command;
import io.gomint.command.CommandOutput;
import io.gomint.command.annotation.*;
import io.gomint.command.validator.EnumValidator;
import io.gomint.entity.EntityPlayer;
import io.gomint.world.Gamemode;

import java.util.Map;

/**
 * @author geNAZt
 * @version 1.0
 */
@Name( "gamemode" )
@Alias( "gm" )
@Description( "Change gamemode of the given player" )
@Overload( {
    @Parameter( name = "mode", validator = EnumValidator.class, arguments = { "s", "c" } )
} )
public class CommandGamemode extends Command {

    @Override
    public CommandOutput execute( EntityPlayer player, String alias, Map<String, Object> arguments ) {
        String mode = (String) arguments.get( "mode" );
        switch ( mode ) {
            case "s":
                player.setGamemode( Gamemode.SURVIVAL );
                break;
            case "c":
                player.setGamemode( Gamemode.CREATIVE );
                break;
        }

        return new CommandOutput().success( "Gamemode has been changed" );
    }

}
