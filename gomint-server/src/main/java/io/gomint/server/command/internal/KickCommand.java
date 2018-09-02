package io.gomint.server.command.internal;

import io.gomint.command.Command;
import io.gomint.command.CommandOutput;
import io.gomint.command.CommandSender;
import io.gomint.command.annotation.*;
import io.gomint.command.validator.TargetValidator;
import io.gomint.command.validator.TextValidator;
import io.gomint.entity.EntityPlayer;

import java.util.Map;

@Name("kick")
@Description("Kick a player")
@Permission("gomint.commands.kick")
@Overload({
        @Parameter(name = "target", validator = TargetValidator.class),
        @Parameter(name = "reason", validator = TextValidator.class, arguments = {"[a-zA-Z0-9ß\\-]+"})
})
@Overload({
        @Parameter(name = "target", validator = TargetValidator.class),
})

public class KickCommand extends Command {

    @Override
    public CommandOutput execute(CommandSender player, String alias, Map<String, Object> arguments) {
        CommandOutput output = new CommandOutput();

        EntityPlayer target = (EntityPlayer) arguments.get("target");
        String reason = (String) arguments.get("reason");

        if (target == null) {
            return output.fail("Invalid argument given. Usage: /kick <player> <reason>");
        }
        if (arguments.containsKey("target") && !arguments.containsKey("reason")) {
            target.disconnect("You have been kicked by an admin.");
            output.success(target.getName() + " has been kicked");
            return output;

        }
        if (arguments.containsKey("target") && arguments.containsKey("reason")) {
            target.disconnect("You have been kicked by an admin. Reason: " + reason);
            output.success(target.getName() + " has been kicked for: " + reason);
            return output;
        }

        return output;
    }
}
