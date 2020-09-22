package io.gomint.testplugin.command;

import io.gomint.command.Command;
import io.gomint.command.CommandOutput;
import io.gomint.command.CommandSender;
import io.gomint.command.annotation.Description;
import io.gomint.command.annotation.Name;
import io.gomint.entity.EntityPlayer;
import io.gomint.plugin.injection.InjectPlugin;
import io.gomint.testplugin.TestPlugin;
import io.gomint.world.World;
import io.gomint.world.generator.CreateOptions;

import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author geNAZt
 */
@Name("test")
@Description("A generic command for testing random stuff")
public class TestCommand extends Command {

    @InjectPlugin
    private TestPlugin plugin;

    @Override
    public CommandOutput execute(CommandSender commandSender, String alias, Map<String, Object> arguments) {
        World world = this.plugin.getServer().createWorld(String.valueOf(ThreadLocalRandom.current().nextInt()), new CreateOptions());
        EntityPlayer player = (EntityPlayer) commandSender;
        player.teleport(world.getSpawnLocation());

        return CommandOutput.successful("Did teleport to %%s", world.getWorldName());
    }
}
