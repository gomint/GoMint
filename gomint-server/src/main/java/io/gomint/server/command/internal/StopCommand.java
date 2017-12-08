package io.gomint.server.command.internal;

import io.gomint.GoMint;
import io.gomint.command.Command;
import io.gomint.command.CommandOutput;
import io.gomint.command.SystemCommand;
import io.gomint.command.annotation.Description;
import io.gomint.command.annotation.Name;
import io.gomint.command.annotation.Permission;
import io.gomint.entity.EntityPlayer;
import io.gomint.server.GoMintServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * @author geNAZt
 * @version 1.0
 */
@Name( "stop" )
@Description( "Stops the GoMint server" )
@Permission( "gomint.command.stop" )
public class StopCommand extends Command implements SystemCommand {

    private static final Logger LOGGER = LoggerFactory.getLogger( StopCommand.class );

    // Player execution
    @Override
    public CommandOutput execute( EntityPlayer player, String alias, Map<String, Object> arguments ) {
        GoMint.instance().shutdown();
        return new CommandOutput().success( "§7[§aSYSTEM§7] §fServer will be stopped" );
    }

    // System execution
    @Override
    public void execute( String[] args ) {
        ( (GoMintServer) GoMint.instance() ).shutdown();
        LOGGER.info( "Shutting down" );
    }

    @Override
    public List<String> complete( String[] args ) {
        return null;
    }

}
