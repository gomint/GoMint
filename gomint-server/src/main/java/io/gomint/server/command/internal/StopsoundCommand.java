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
import io.gomint.command.validator.StringValidator;
import io.gomint.server.entity.EntityPlayer;

import java.util.Map;

/**
 * @author lukeeey
 * @version 1.0
 */
@Name( "stopsound" )
@Description( "Stops a sound." )
@Permission( "gomint.command.stopsound" )
@Overload( {
    @Parameter( name = "player", validator = TargetValidator.class ),
    @Parameter( name = "sound", validator = StringValidator.class, optional = true )
} )
public class StopsoundCommand extends Command {

    @Override
    public CommandOutput execute( CommandSender player, String alias, Map<String, Object> arguments ) {
        CommandOutput output = new CommandOutput();
        EntityPlayer target = (EntityPlayer) arguments.get( "player" );

        if( arguments.containsKey( "sound" ) ) {
            target.stopSound( (String) arguments.get( "sound" ) );
            return output.success( "Stopped sound '%%s' for %%s", (String) arguments.get( "sound" ), target.getName() );
        }

        target.stopSound();
        return output.success( "Stopped all sounds for %%s", target.getName() );
    }

}
