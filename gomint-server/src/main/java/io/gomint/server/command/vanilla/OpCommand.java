package io.gomint.server.command.vanilla;

import io.gomint.command.Command;
import io.gomint.command.CommandOutput;
import io.gomint.command.CommandSender;
import io.gomint.command.PlayerCommandSender;
import io.gomint.command.annotation.*;
import io.gomint.command.validator.TargetValidator;
import io.gomint.entity.EntityPlayer;

import java.util.Map;

/**
 * @author lukeeey
 * @version 1.0
 */
@Name("op")
@Description("Grants operator status to a player.")
@Permission("gomint.command.op")
@Overload({
    @Parameter(name = "player", validator = TargetValidator.class, optional = true)
})
public class OpCommand extends Command {

    @Override
    public CommandOutput execute(CommandSender sender, String alias, Map<String, Object> arguments) {
        EntityPlayer target = (EntityPlayer) arguments.get("player");
        if (target == null) {
            if (sender instanceof PlayerCommandSender) {
                target = (EntityPlayer) sender;
            } else {
                return CommandOutput.failure("Please provide a player to op");
            }
        }

        if (target.isOp()) {
            return CommandOutput.failure("Could not op (already op or higher): " + target.getName());
        }

        target.setOp(true);
        target.sendMessage("You have been opped");
        return CommandOutput.successful("Opped: " + target.getName());
    }
}
