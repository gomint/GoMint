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
import io.gomint.command.validator.TargetValidator;
import io.gomint.command.validator.TextValidator;
import io.gomint.server.entity.EntityPlayer;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author lukeeey
 * @version 1.0
 */
@Name( "title" )
@Description( "Controls screen titles." )
@Permission( "gomint.command.title" )
@Overload( {
    @Parameter( name = "player", validator = TargetValidator.class ),
    @Parameter( name = "clear", validator = EnumValidator.class, arguments = { "clear" } )
} )
@Overload( {
    @Parameter( name = "player", validator = TargetValidator.class ),
    @Parameter( name = "reset", validator = EnumValidator.class, arguments = { "reset" } )
} )
@Overload( {
    @Parameter( name = "player", validator = TargetValidator.class ),
    @Parameter( name = "type", validator = EnumValidator.class, arguments = { "title", "subtitle", "actionbar" } ),
    @Parameter( name = "text", validator = TextValidator.class )
} )
@Overload( {
    @Parameter( name = "player", validator = TargetValidator.class ),
    @Parameter( name = "type", validator = EnumValidator.class, arguments = { "times" } ),
    @Parameter( name = "fadeIn", validator = IntegerValidator.class ),
    @Parameter( name = "stay", validator = IntegerValidator.class ),
    @Parameter( name = "fadeOut", validator = IntegerValidator.class )
} )
public class TitleCommand extends Command {

    @Override
    public CommandOutput execute( CommandSender player, String alias, Map<String, Object> arguments ) {
        CommandOutput output = new CommandOutput();
        EntityPlayer target = (EntityPlayer) arguments.get( "player" );

        if( target == null ) {
            return output.fail( "No targets matched selector" );
        }
        if( arguments.containsKey( "clear" ) ) {
            target.clearTitle();
        }
        if( arguments.containsKey( "reset" ) ) {
            target.resetTitle();
        }

        if( arguments.containsKey( "type" ) ) {
            switch( (String) arguments.get( "type" ) ) {
                case "title":
                    target.sendTitle( (String) arguments.get( "text" ) );
                    break;
                case "subtitle":
                    target.sendTitle( null, (String) arguments.get( "text" ) );
                    break;
                case "actionbar":
                    target.sendActionbar( (String) arguments.get( "text" ) );
                    break;
                case "times":
                    // Doesn't work yet
                    target.sendTitle( null, null, (int) arguments.get( "fadeIn" ), (int) arguments.get( "stay" ), (int) arguments.get( "fadeOut" ), TimeUnit.SECONDS );
                    break;
            }
        }

        return output.success( "Title command successfully executed" );
    }

}
