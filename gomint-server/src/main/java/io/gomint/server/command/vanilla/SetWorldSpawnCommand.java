package io.gomint.server.command.vanilla;

import io.gomint.command.Command;
import io.gomint.command.CommandOutput;
import io.gomint.command.CommandSender;
import io.gomint.command.PlayerCommandSender;
import io.gomint.command.annotation.Description;
import io.gomint.command.annotation.Name;
import io.gomint.command.annotation.Overload;
import io.gomint.command.annotation.Parameter;
import io.gomint.command.annotation.Permission;
import io.gomint.command.validator.BlockPositionValidator;
import io.gomint.math.Location;
import io.gomint.server.entity.EntityPlayer;
import io.gomint.server.world.WorldAdapter;

import java.util.Map;

/**
 * @author Clockw1seLrd
 * @version 1.0
 */
@Name( "setworldspawn" )
@Description( "Sets the world spawn at the current location where the executor stands" )
@Permission( "gomint.command.setworldspawn" )
@Overload( {
    @Parameter( name = "spawnPoint", validator = BlockPositionValidator.class, optional = true )
} )
public class SetWorldSpawnCommand extends Command {

    @Override
    public CommandOutput execute( CommandSender sender, String alias, Map<String, Object> arguments ) {
        CommandOutput output = new CommandOutput();

        if (! ( sender instanceof PlayerCommandSender ) ) {
            return output.fail( "Executor is required to be a player" );
        }

        EntityPlayer executor = ( EntityPlayer ) sender;
        WorldAdapter affectedWorld = executor.getWorld();
        Location worldSpawnLocation = ( Location ) arguments.getOrDefault( "spawnPoint", executor.getLocation() );

        worldSpawnLocation.setX( (float) Math.floor( worldSpawnLocation.getX() ) );
        worldSpawnLocation.setY( (float) Math.floor( worldSpawnLocation.getY() ) );
        worldSpawnLocation.setZ( (float) Math.floor( worldSpawnLocation.getZ() ) );

        affectedWorld.setSpawnLocation( worldSpawnLocation );

        return output.success( "Set the world spawn point to (%.1f, %.1f, %.1f)" );
    }

}
