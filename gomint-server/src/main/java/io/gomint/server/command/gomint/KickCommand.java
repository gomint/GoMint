package io.gomint.server.command.gomint;

import io.gomint.command.Command;
import io.gomint.command.CommandOutput;
import io.gomint.command.CommandSender;
import io.gomint.command.PlayerCommandSender;
import io.gomint.command.annotation.*;
import io.gomint.command.validator.TargetValidator;
import io.gomint.command.validator.TextValidator;
import io.gomint.entity.EntityPlayer;

import java.util.Map;

/**
 * @author lukeeey
 * @version 1.1
 */
@Name( "kick" )
@Description( "Kick a player from the server." )
@Permission( "gomint.command.kick" )
@Overload( {
    @Parameter( name = "player", validator = TargetValidator.class ),
    @Parameter( name = "reason", validator = TextValidator.class, optional = true )
} )
public class KickCommand extends Command {

    @Override
    public CommandOutput execute( CommandSender commandSender, String s, Map<String, Object> map ) {
        CommandOutput commandOutput = new CommandOutput();

        // check if the target is entered
        if ( !map.containsKey( "player" ) ) {
            return commandOutput.fail( "Please specify a player." );
        }

        EntityPlayer target = (EntityPlayer) map.get( "player" );

        // get the sender's name
        String sender = commandSender instanceof PlayerCommandSender ? target.getName() : "CONSOLE";

        // get string reason
        String reason = (String) map.get( "reason" );

        // the kick message that'll show up in the target's screen
        String kick_message;
        if ( !map.containsKey( "reason" ) ) {
            kick_message = "You have been kicked out by " + sender + ".";
        } else {
            kick_message = "You have been kicked out by " + sender + ". Reason: " + reason;
        }

        // kick the specified target
        target.disconnect( kick_message );
        return commandOutput.success( target.getName() + " has been kicked out." );
    }
}
