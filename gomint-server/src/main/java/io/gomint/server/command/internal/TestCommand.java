package io.gomint.server.command.internal;

import io.gomint.command.Command;
import io.gomint.command.CommandExecutor;
import io.gomint.command.CommandOutput;
import io.gomint.command.validator.FloatValidator;
import io.gomint.command.validator.StringValidator;
import io.gomint.entity.Player;
import io.gomint.server.command.CommandManager;

import java.util.Map;

/**
 * @author geNAZt
 * @version 1.0
 */
public class TestCommand extends CommandExecutor {

    public TestCommand( CommandManager commandManager ) {
        Command command = new Command( "test" );
        command.description( "Test for internal stuff" ).executor( this ).permission( "gomint.commands.test" );
        command.overload().param( "pos", new StringValidator( "[A-Za-z0-9_]+") ).param( "t1", new FloatValidator(), true );

        commandManager.register( null, command );
    }

    @Override
    public CommandOutput execute( Player player, Map<String, Object> arguments ) {
        return new CommandOutput().success( "Got %%s", arguments.get( "pos" ) );
    }

}
