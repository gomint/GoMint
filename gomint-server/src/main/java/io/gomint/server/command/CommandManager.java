package io.gomint.server.command;

import com.google.common.reflect.ClassPath;
import io.gomint.command.Command;
import io.gomint.plugin.Plugin;
import io.gomint.server.entity.CommandPermissionMagicNumbers;
import io.gomint.server.entity.EntityPlayer;
import io.gomint.server.network.packet.PacketAvailableCommands;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author geNAZt
 * @version 1.0
 */
public class CommandManager {

    private Map<String, CommandHolder> commands = new ConcurrentHashMap<>();
    private Map<String, Plugin> commandPlugins = new ConcurrentHashMap<>();

    public CommandManager() {
        // Register all internal commands
        try {
            for ( ClassPath.ClassInfo classInfo : ClassPath.from( ClassLoader.getSystemClassLoader() ).getTopLevelClasses( "io.gomint.server.command.internal" ) ) {
                Class<?> cmdClass = classInfo.load();
                cmdClass.getConstructor( CommandManager.class ).newInstance( this );
            }
        } catch ( IOException | InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e ) {
            e.printStackTrace();
        }
    }

    public void register( Plugin plugin, Command commandBuilder ) {
        // Check if command is complete
        if ( commandBuilder.getName() == null ||
                commandBuilder.getDescription() == null ||
                commandBuilder.getExecutor() == null ) {
            throw new IllegalStateException( "Name, description and executor can't be null" );
        }

        // Check for name collision
        CommandHolder holder = this.commands.get( commandBuilder.getName() );
        if ( holder != null ) {
            // Remap the old command to its fallback
            Plugin originalPlugin = this.commandPlugins.get( commandBuilder.getName() );
            String cmdName;
            if ( originalPlugin != null ) {
                cmdName = originalPlugin.getName() + ":" + holder.getName();
            } else {
                cmdName = "gomint:" + holder.getName();
            }

            CommandHolder commandHolder = new CommandHolder(
                    cmdName,
                    holder.getDescription(),
                    holder.getAlias(),
                    holder.getCommandPermission(),
                    holder.getPermission(),
                    holder.getExecutor(),
                    holder.getOverload()
            );

            this.commands.put( cmdName, commandHolder );
            this.commandPlugins.put( cmdName, originalPlugin );
        }

        // Create a new holder
        holder = new CommandHolder(
                commandBuilder.getName(),
                commandBuilder.getDescription(),
                commandBuilder.getAlias(),
                CommandPermissionMagicNumbers.NORMAL,
                commandBuilder.getPermission(),
                commandBuilder.getExecutor(),
                commandBuilder.getOverload() );

        // Store the command for usage
        this.commands.put( commandBuilder.getName(), holder );
        if ( plugin != null ) {
            this.commandPlugins.put( commandBuilder.getName(), plugin );
        }
    }

    public PacketAvailableCommands createPacket( EntityPlayer player ) {
        List<CommandHolder> holders = new ArrayList<>();
        for ( CommandHolder holder : this.commands.values() ) {
            if ( player.hasPermission( holder.getPermission() ) ) {
                holders.add( holder );
            }
        }

        CommandPreprocessor preprocessor = new CommandPreprocessor( holders );
        return preprocessor.getCommandsPacket();
    }

    public Collection<CommandHolder> getCommands() {
        return commands.values();
    }

}
