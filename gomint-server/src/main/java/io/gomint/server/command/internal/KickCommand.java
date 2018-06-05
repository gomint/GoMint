package io.gomint.server.command.internal;

import io.gomint.command.Command;
import io.gomint.command.CommandOutput;
import io.gomint.command.annotation.*;
import io.gomint.command.validator.StringValidator;
import io.gomint.command.validator.TargetValidator;
import io.gomint.entity.EntityPlayer;

import java.util.Map;

@Name("kick")
@Description("Kick a player")
@Permission("gomint.commands.kick")
@Overload({
        @Parameter(name = "target", validator = TargetValidator.class),
        @Parameter(name = "reason", validator = StringValidator.class, arguments = {"[a-zA-Z0-9ß\\-]+"})
})
@Overload({
        @Parameter(name = "target", validator = TargetValidator.class),
})

public class KickCommand extends Command {

    @Override
    public CommandOutput execute(EntityPlayer entityPlayer, String s, Map<String, Object> map) {
        CommandOutput output = new CommandOutput();

        EntityPlayer target = (EntityPlayer) map.get("target");
        String reason = (String) map.get("reason");

        if (target == null) {
            return output.fail("Invalid argument given. Usage: /kick <player> <reason>");
        }
        if (map.containsKey("target") && !map.containsKey("reason")) {
            target.disconnect("You have been kicked by an admin.");
            output.success(target.getName() + " has been kicked");
            return output;

        }
        if (map.containsKey("target") && map.containsKey("reason")) {
            target.disconnect("You have been kicked by an admin. Reason: " + reason);
            output.success(target.getName() + " has been kicked for: " + reason);
            return output;
        }

        return output;
    }
}
