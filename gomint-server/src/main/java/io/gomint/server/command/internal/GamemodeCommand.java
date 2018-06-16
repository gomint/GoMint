package io.gomint.server.command.internal;

import io.gomint.ChatColor;
import io.gomint.GoMint;
import io.gomint.command.Command;
import io.gomint.command.CommandOutput;
import io.gomint.command.annotation.Description;
import io.gomint.command.annotation.Name;
import io.gomint.command.annotation.Overload;
import io.gomint.command.annotation.Parameter;
import io.gomint.command.annotation.Permission;
import io.gomint.command.validator.EnumValidator;
import io.gomint.command.validator.TargetValidator;
import io.gomint.entity.EntityPlayer;
import io.gomint.world.Gamemode;

import java.util.Map;
import java.util.Random;

@Name("gamemode")
@Description("Sets a player's game mode")
@Permission("gomint.gommands.gamemode")
@Overload({
    @Parameter(name = "gameMode", validator = EnumValidator.class, arguments = {"0", "s", "survival", "1", "c", "creative", "2", "a", "adventure"}),
    @Parameter(name = "target", validator = TargetValidator.class, optional = true)
})
@Overload({
    @Parameter(name = "gameMode", validator = EnumValidator.class, arguments = {"0", "s", "survival", "1", "c", "creative", "2", "a", "adventure"}),
    @Parameter(name = "specialTarget", validator = EnumValidator.class, arguments = {"@a", "@e", "@p", "@r", "@s"})
})
public class GamemodeCommand extends Command {

    @Override
    public CommandOutput execute(EntityPlayer player, String alias, Map<String, Object> arguments) {
        String mode = (String) arguments.get("gameMode");
        mode = mode.toLowerCase();
        Gamemode gamemode;
        switch (mode) {
            case "0":
            case "s":
            case "survival":
                gamemode = Gamemode.SURVIVAL;
                break;
            case "1":
            case "c":
            case "creative":
                gamemode = Gamemode.CREATIVE;
                break;
            case "2":
            case "a":
            case "adventure":
                gamemode = Gamemode.ADVENTURE;
                break;
            default:
                throw new AssertionError(ChatColor.RED + "Unknown gamemode accepted by EnumValidator");
        }
        EntityPlayer target = (EntityPlayer) arguments.getOrDefault("target", player);
        if (arguments.containsKey("specialTarget")) {
            String specialtarget = (String) arguments.get("specialTarget");
            specialtarget = specialtarget.toLowerCase();
            switch (specialtarget) {
                case "@a":
                    for (EntityPlayer p : GoMint.instance().getPlayers()) {
                        p.setGamemode(gamemode);
                    }
                    return new CommandOutput().success("Set own game mode to %s", gamemode.name().toLowerCase());
                case  "@e":
                    //nothing to do, this option was for entities.
                    break;
                case "@p":
                    //nothing to do, the command executor is the default target
                    break;
                case "@r":
                    int random = new Random().nextInt(GoMint.instance().getPlayers().toArray().length);
                    EntityPlayer p = (EntityPlayer) GoMint.instance().getPlayers().toArray()[random];
                    p.setGamemode(gamemode);
                    return new CommandOutput().success("Set own game mode to %s", gamemode.name().toLowerCase());
                case "@s":
                    return new CommandOutput().success("Set own game mode to %s", gamemode.name().toLowerCase());
                default:
                    throw new AssertionError(ChatColor.RED + "Syntax error: Unexpected specialTarget.");
            }
        }
        target.setGamemode(gamemode);
        if (target == player) {
            return new CommandOutput().success("Your game mode has been updated to %s", gamemode.name().toLowerCase());
        }
        return new CommandOutput().success("%s's game mode is now %s", target.getDisplayName(), gamemode.name().toLowerCase());
    }
}
