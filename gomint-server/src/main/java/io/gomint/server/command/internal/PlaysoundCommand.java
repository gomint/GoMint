package io.gomint.server.command.internal;

import io.gomint.command.Command;
import io.gomint.command.CommandOutput;
import io.gomint.command.CommandSender;
import io.gomint.command.annotation.Description;
import io.gomint.command.annotation.Name;
import io.gomint.command.annotation.Parameter;
import io.gomint.command.annotation.Permission;
import io.gomint.command.annotation.Overload;
import io.gomint.command.validator.TargetValidator;
import io.gomint.command.validator.BlockPositionValidator;
import io.gomint.command.validator.StringValidator;
import io.gomint.command.validator.FloatValidator;
import io.gomint.server.entity.EntityPlayer;
import io.gomint.world.Sound;
import io.gomint.math.Vector;

import java.util.Map;

/**
 * @author lukeeey
 * @version 1.0
 */
@Name( "playsound" )
@Description( "Plays a sound." )
@Permission( "gomint.command.playsound" )
@Overload( {
    @Parameter( name = "sound", validator = StringValidator.class ),
    @Parameter( name = "player", validator = TargetValidator.class, optional = true ),
    @Parameter( name = "position", validator = BlockPositionValidator.class, optional = true ),
    @Parameter( name = "volume", validator = FloatValidator.class, optional = true ),
    @Parameter( name = "pitch", validator = FloatValidator.class, optional = true ),
    @Parameter( name = "minimumVolume", validator = FloatValidator.class, optional = true )
} )
public class PlaysoundCommand extends Command {

    @Override
    public CommandOutput execute( CommandSender player, String alias, Map<String, Object> arguments ) {
        CommandOutput output = new CommandOutput();
        EntityPlayer target = (EntityPlayer) player;

        if( arguments.containsKey( "player" ) ) {
            if( arguments.get( "player" ) == null ) {
                return output.fail( "No targets matched selector" );
            }
            target = (EntityPlayer) arguments.get( "player" );
        }

        Vector position = target.getPosition();
        byte pitch = 0;

        if( arguments.containsKey( "position" ) ) {
            position = (Vector) arguments.get( "position" );
        }
        if( arguments.containsKey( "pitch" ) ) {
            pitch = (byte) arguments.get( "pitch" );
        }

        //target.playSound( position, "", pitch );

        return output;
    }

}
