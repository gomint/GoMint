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
import io.gomint.command.validator.IntegerValidator;
import io.gomint.server.entity.EntityPlayer;

import java.util.Map;

/**
 * @author lukeeey
 * @version 1.0
 */
@Name( "xp" )
@Description( "Adds or removes player experience." )
@Permission( "gomint.command.xp" )
@Overload( {
    @Parameter( name = "amount", validator = IntegerValidator.class ),
    @Parameter( name = "player", validator = TargetValidator.class, optional = true ),
} )
@Overload( {
    @Parameter( name = "amount", validator = IntegerValidator.class ),
    @Parameter( name = "player", validator = TargetValidator.class, optional = true ),
} )
public class XPCommand extends Command {

    @Override
    public CommandOutput execute( CommandSender player, String alias, Map<String, Object> arguments ) {
        CommandOutput output = new CommandOutput();
        EntityPlayer target = (EntityPlayer) player;

        if( arguments.containsKey( "player" ) || player instanceof ConsoleCommandSender ) {
            if( arguments.get( "player" ) == null ) {
                return output.fail( "No targets matched selector" );
            }
            target = (EntityPlayer) arguments.get( "player" );
        }

        target.addXP( (int) arguments.get( "amount" ) );
        return output;
    }

}
