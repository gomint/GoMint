package io.gomint.server.command.internal;

import io.gomint.command.Command;
import io.gomint.command.CommandOutput;
import io.gomint.command.CommandSender;
import io.gomint.command.ConsoleCommandSender;
import io.gomint.command.annotation.Description;
import io.gomint.command.annotation.Name;
import io.gomint.command.annotation.Parameter;
import io.gomint.command.annotation.Permission;
import io.gomint.command.annotation.Overload;
import io.gomint.command.validator.TargetValidator;
import io.gomint.command.validator.EnumValidator;
import io.gomint.command.validator.IntegerValidator;
import io.gomint.server.entity.EntityPlayer;
import io.gomint.world.Gamemode;

import java.util.Map;

/**
 * @author lukeeey
 * @version 1.0
 */
@Name( "gamemode" )
@Description( "Sets a player's game mode." )
@Permission( "gomint.command.gamemode" )
@Overload( {
    @Parameter( name = "mode", validator = EnumValidator.class, arguments = { "a", "adventure", "c", "creative", "s", "survival", "sp", "spectator" } ),
    @Parameter( name = "player", validator = TargetValidator.class, optional = true )
} )
@Overload( {
    @Parameter( name = "mode", validator = IntegerValidator.class ),
    @Parameter( name = "player", validator = TargetValidator.class, optional = true )
} )
public class GamemodeCommand extends Command {

    @Override
    public CommandOutput execute( CommandSender player, String alias, Map<String, Object> arguments ) {
        CommandOutput output = new CommandOutput();
        EntityPlayer target;

        if( arguments.containsKey( "player" ) || player instanceof ConsoleCommandSender ) {
            if( arguments.get( "player" ) == null ) {
                return output.fail( "No targets matched selector" );
            }
            target = (EntityPlayer) arguments.get( "player" );
        } else {
            target = (EntityPlayer) player;
        }

        Gamemode mode;
        switch( String.valueOf( arguments.get( "mode" ) ) ) {
			case "0":
            case "s":
            case "survival":
                mode = Gamemode.SURVIVAL;
                break;
			case "1":
            case "c":
            case "creative":
                mode = Gamemode.CREATIVE;
                break;
			case "2":
            case "a":
            case "adventure":
                mode = Gamemode.ADVENTURE;
                break;
			case "3":
            case "sp":
            case "spectator":
                mode = Gamemode.SPECTATOR;
                break;
            default:
                return output.fail( "Unknown game mode" );
        }

        target.setGamemode( mode );
        target.sendMessage( "Your game mode has been updated to " + mode.getGamemodeName() );

        if( target == player ) {
            output.success( "Set own game mode to %%s", mode.getGamemodeName() );
        } else {
            output.success( "Set %%s's game mode to %%s", target.getName(), mode.getGamemodeName() );
        }
        return output;
    }

}
