package io.gomint.server.command.internal;

import io.gomint.command.Command;
import io.gomint.command.CommandOutput;
import io.gomint.command.CommandSender;
import io.gomint.command.annotation.Description;
import io.gomint.command.annotation.Name;
import io.gomint.command.annotation.Parameter;
import io.gomint.command.annotation.Permission;
import io.gomint.command.annotation.Overload;
import io.gomint.command.validator.EnumValidator;
import io.gomint.command.validator.IntegerValidator;
import io.gomint.entity.EntityPlayer;
import io.gomint.world.Difficulty;

import java.util.Map;

/**
 * @author lukeeey
 * @version 1.0
 */
@Name( "difficulty" )
@Description( "Sets the difficulty level." )
@Permission( "gomint.command.difficulty" )
@Overload( {
    @Parameter( name = "difficulty", validator = EnumValidator.class, arguments = { "e", "easy", "h", "hard", "n", "normal", "p", "peaceful" } ),
} )
@Overload( {
    @Parameter( name = "difficulty", validator = IntegerValidator.class )
} )
public class DifficultyCommand extends Command {

    @Override
    public CommandOutput execute( CommandSender player, String alias, Map<String, Object> arguments ) {
        CommandOutput output = new CommandOutput();

        Difficulty difficulty;
        switch( String.valueOf( arguments.get( "difficulty" ) ) ) {
			case "0":
            case "p":
            case "peaceful":
                difficulty = Difficulty.PEACEFUL;
                break;
			case "1":
            case "e":
            case "easy":
                difficulty = Difficulty.EASY;
                break;
			case "2":
            case "n":
            case "normal":
                difficulty = Difficulty.NORMAL;
                break;
			case "3":
            case "h":
            case "hard":
                difficulty = Difficulty.HARD;
                break;
            default:
                return output.fail( "Unknown difficulty" );
        }

        ((EntityPlayer) player).getWorld().setDifficulty( difficulty );
        return output.success( "Set game difficulty to %%s", difficulty.name() );
    }

}
