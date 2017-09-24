package io.gomint.server.command.internal;

import io.gomint.GoMint;
import io.gomint.command.Command;
import io.gomint.command.CommandExecutor;
import io.gomint.command.CommandOutput;
import io.gomint.command.validator.StringValidator;
import io.gomint.entity.Player;
import io.gomint.server.GoMintServer;
import io.gomint.server.command.CommandManager;

import java.util.Map;

/**
 * @author geNAZt
 * @version 1.0
 */
public class StopCommand extends CommandExecutor {

    public StopCommand( CommandManager commandManager ) {
        Command command = new Command( "stop" );
        command.description( "Stops the GoMint server" )
                .permission( "gomint.commands.stop" )
                .executor( this );

        commandManager.register( null, command );
    }

    @Override
    public CommandOutput execute( Player player, Map<String, Object> arguments ) {
        ( (GoMintServer) GoMint.instance() ).shutdown();
        return new CommandOutput().success( "§7[§aSYSTEM§7] §fServer will be stopped" );
    }

}
