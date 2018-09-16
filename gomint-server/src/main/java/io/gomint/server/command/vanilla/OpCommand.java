package io.gomint.server.command.vanilla;

import io.gomint.command.Command;
import io.gomint.command.CommandOutput;
import io.gomint.command.CommandSender;
import io.gomint.command.annotation.*;
import io.gomint.command.validator.TargetValidator;
import io.gomint.server.entity.EntityPlayer;

import java.util.Map;

/**
 * @author lukeeey
 * @version 1.0
 */
@Name( "op" )
@Description( "Grants operator status to a player." )
@Permission( "gomint.command.op" )
@Overload( {
    @Parameter( name = "player", validator = TargetValidator.class )
} )
public class OpCommand extends Command {

    @Override
    public CommandOutput execute( CommandSender sender, String alias, Map<String, Object> arguments ) {
        CommandOutput output = new CommandOutput();
        EntityPlayer target = (EntityPlayer) arguments.get( "player" );

        if( target.isOp() ) {
            return output.fail( "Could not op (already op or higher): " + target.getName() );
        }

        target.setOp( true );
        target.sendMessage( "You have been opped" );
        return output.success( "Opped: " + target.getName() );
    }
}
