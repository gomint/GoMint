

package io.gomint.server.command.vanilla;

import io.gomint.GoMint;
import io.gomint.command.Command;
import io.gomint.command.CommandOutput;
import io.gomint.command.CommandSender;
import io.gomint.command.PlayerCommandSender;
import io.gomint.command.annotation.*;
import io.gomint.command.validator.BlockPositionValidator;
import io.gomint.command.validator.StringValidator;
import io.gomint.command.validator.TargetValidator;
import io.gomint.entity.EntityPlayer;
import io.gomint.math.BlockPosition;
import io.gomint.math.Location;
import io.gomint.world.World;

import java.util.Map;

/**
 * @author geNAZt
 * @version 1.0
 */
@Name("tp")
@Description("Teleport to a given place or entity")
@Permission("gomint.command.tp")
@Overload({
    @Parameter(name = "world", validator = StringValidator.class, arguments = {"[a-zA-Z0-9ß\\-]+"}),
    @Parameter(name = "position", validator = BlockPositionValidator.class)
})
@Overload({
    @Parameter(name = "toTarget", validator = TargetValidator.class)
})
@Overload({
    @Parameter(name = "position", validator = BlockPositionValidator.class)
})
@Overload({
    @Parameter(name = "target", validator = TargetValidator.class),
    @Parameter(name = "position", validator = BlockPositionValidator.class, optional = true)
})
@Overload({
    @Parameter(name = "target", validator = TargetValidator.class),
    @Parameter(name = "toTarget", validator = TargetValidator.class)
})
public class TPCommand extends Command {

    @Override
    public CommandOutput execute(CommandSender sender, String alias, Map<String, Object> arguments) {
        // Check for source
        EntityPlayer source = (sender instanceof PlayerCommandSender) ? (EntityPlayer) sender : null;
        if (arguments.containsKey("target")) {
            source = (EntityPlayer) arguments.get("target");
        }

        if (source == null) {
            return CommandOutput.failure("No source for teleport given");
        }

        // Check for entity teleportation
        if (arguments.containsKey("toTarget")) {
            EntityPlayer entity = (EntityPlayer) arguments.get("toTarget");
            source.teleport(entity.getLocation());
            return CommandOutput.successful("%%s has been teleported to %%s", source.getName(), entity.getName());
        }

        // Do we have a world given?
        Location to = new Location(source.getWorld(), 0, 0, 0);
        if (arguments.containsKey("world")) {
            World world = GoMint.instance().getWorld((String) arguments.get("world"));
            if (world == null) {
                return CommandOutput.failure("World %%s could not be found", arguments.get("world"));
            } else {
                to.setWorld(world);
            }
        }

        BlockPosition position = (BlockPosition) arguments.get("position");
        to.setX(position.getX());
        to.setY(position.getY());
        to.setZ(position.getZ());

        source.teleport(to);
        return CommandOutput.successful("%%s has been teleported to %%s, %%s, %%s, %%s", source.getName(), to.getWorld().getWorldName(), to.getX(), to.getY(), to.getZ());
    }
}
