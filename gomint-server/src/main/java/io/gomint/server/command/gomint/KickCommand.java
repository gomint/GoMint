package io.gomint.server.command.gomint;

import io.gomint.command.Command;
import io.gomint.command.CommandOutput;
import io.gomint.command.CommandSender;
import io.gomint.command.annotation.*;
import io.gomint.command.validator.TargetValidator;
import io.gomint.command.validator.TextValidator;
import io.gomint.server.entity.EntityPlayer;

import java.util.Map;

/**
 * @author lukeeey
 * @version 1.0
 */

@Name("kick")
@Description( "Kick a player from the server." )
@Permission("gomint.command.kick")
@Overload({
    @Parameter(name = "player", validator = TargetValidator.class),
    @Parameter(name = "reason", validator = TextValidator.class, optional = true)
})
public class KickCommand extends Command {

    @Override
    public CommandOutput execute(CommandSender sender, String alias, Map<String, Object> arguments) {
        CommandOutput output = new CommandOutput();
        EntityPlayer target = (EntityPlayer) arguments.get("player");
        String reason = "Kicked by an operator.";

        if (target == null) {
            return output.fail("You must provide a player!");
        }

        if (arguments.containsKey("reason")) {
            reason = (String) arguments.get("reason");
        }

        target.disconnect(reason);
        return new CommandOutput().success("Kicked %%s from the server", target.getDisplayName());
    }
}
