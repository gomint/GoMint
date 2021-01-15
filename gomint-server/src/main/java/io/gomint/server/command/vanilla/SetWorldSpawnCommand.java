package io.gomint.server.command.vanilla;

import io.gomint.command.Command;
import io.gomint.command.CommandOutput;
import io.gomint.command.CommandSender;
import io.gomint.command.PlayerCommandSender;
import io.gomint.command.annotation.*;
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
@Name("setworldspawn")
@Description("Sets the world spawn")
@Permission("gomint.command.setworldspawn")
@Overload({
    @Parameter(name = "spawnPoint", validator = BlockPositionValidator.class, optional = true)
})
public class SetWorldSpawnCommand extends Command {

    @Override
    public CommandOutput execute(CommandSender<?> sender, String alias, Map<String, Object> arguments) {
        if (!(sender instanceof PlayerCommandSender)) {
            return CommandOutput.failure("Executor is required to be a player");
        }

        EntityPlayer executor = (EntityPlayer) sender;
        WorldAdapter affectedWorld = executor.world();
        Location worldSpawnLocation = executor.location();

        // Handling argument: spawnPoint
        BlockPosition spawnPoint = (BlockPosition) arguments.get("spawnPoint");
        if (spawnPoint != null) {
            worldSpawnLocation.setX(spawnPoint.x());
            worldSpawnLocation.setY(spawnPoint.y());
            worldSpawnLocation.setZ(spawnPoint.z());
        }

        this.floorLocation(worldSpawnLocation);
        affectedWorld.spawnLocation(worldSpawnLocation);

        return CommandOutput.successful(String.format("Set the world spawn point to (%.1f, %.1f, %.1f)",
            worldSpawnLocation.getX(),
            worldSpawnLocation.getY(),
            worldSpawnLocation.getZ()));
    }

    private void floorLocation(Location location) {
        location.setX((float) Math.floor(location.getX()));
        location.setY((float) Math.floor(location.getY()));
        location.setZ((float) Math.floor(location.getZ()));
    }
}
