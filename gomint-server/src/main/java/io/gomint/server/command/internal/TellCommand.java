package io.gomint.server.command.internal;

import io.gomint.ChatColor;
import io.gomint.command.Command;
import io.gomint.command.CommandOutput;
import io.gomint.command.CommandSender;
import io.gomint.command.ConsoleCommandSender;
import io.gomint.command.annotation.*;
import io.gomint.command.validator.TextValidator;
import io.gomint.command.validator.TargetValidator;
import io.gomint.server.entity.EntityPlayer;

import java.util.Map;

/**
 * @author lukeeey
 * @version 1.0
 */
@Name( "tell" )
@Alias( "w" )
@Alias( "msg" )
@Description( "Sends a private message to a player." )
@Permission( "gomint.command.tell" )
@Overload( {
    @Parameter( name = "target", validator = TargetValidator.class ),
    @Parameter( name = "message", validator = TextValidator.class )
} )
public class TellCommand extends Command {

    @Override
    public CommandOutput execute( CommandSender player, String alias, Map<String, Object> arguments ) {
        CommandOutput output = new CommandOutput();
        EntityPlayer target = (EntityPlayer) arguments.get( "target" );

        if( target == null ) {
            return output.fail( "No targets matched selector" );
        }

        String name = (player instanceof ConsoleCommandSender) ? "CONSOLE" : ((EntityPlayer) player).getName();

        target.sendMessage( ChatColor.GRAY + name + " whispers " + (String) arguments.get( "message" ) );
        return output.success( ChatColor.GRAY + "[me -> %%s] %%s", target.getName(), (String) arguments.get( "message" ) );
    }

}
