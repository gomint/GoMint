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
import io.gomint.math.BlockPosition;
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

        EntityPlayer executor = (EntityPlayer) sender;
        WorldAdapter affectedWorld = executor.getWorld();
        Location worldSpawnLocation = executor.getLocation();

        // Handling argument: spawnPoint
        BlockPosition spawnPoint = (BlockPosition) arguments.get("spawnPoint");
        if ( spawnPoint != null ) {
            worldSpawnLocation.setX(spawnPoint.getX());
            worldSpawnLocation.setY(spawnPoint.getY());
            worldSpawnLocation.setZ(spawnPoint.getZ());
        }

        this.floorLocation(worldSpawnLocation);
        affectedWorld.setSpawnLocation( worldSpawnLocation );

        return output.success( "Set the world spawn point to (%.1f, %.1f, %.1f)" );
    }

    private void floorLocation( Location location ) {
        location.setX( (float) Math.floor( location.getX() ) );
        location.setY( (float) Math.floor( location.getY() ) );
        location.setZ( (float) Math.floor( location.getZ() ) );
    }

}
