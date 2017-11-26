package io.gomint.server.command;

import com.google.common.reflect.ClassPath;
import io.gomint.command.Command;
import io.gomint.command.SystemCommand;
import io.gomint.command.annotation.Name;
import io.gomint.plugin.Plugin;
import io.gomint.server.GoMintServer;
import io.gomint.server.entity.CommandPermission;
import io.gomint.server.entity.EntityPlayer;
import io.gomint.server.network.packet.PacketAvailableCommands;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.function.Function;

/**
 * @author geNAZt
 * @version 1.0
 */
public class CommandManager {

    private static final Logger LOGGER = LoggerFactory.getLogger( CommandManager.class );

    private Map<String, CommandHolder> commands = new HashMap<>();
    private Map<String, Plugin> commandPlugins = new HashMap<>();
    private Map<String, SystemCommand> systemCommands = new HashMap<>();
    private Map<String, SubCommand> subCommands = new HashMap<>();

    /**
     * Create a new command manager
     */
    public CommandManager() {
        // Register all internal commands
        try {
            for ( ClassPath.ClassInfo classInfo : GoMintServer.getClassPath().getTopLevelClasses( "io.gomint.server.command.internal" ) ) {
                // Check for system only commands
                Class<?> cmdClass = classInfo.load();
                Object commandObject = null;

                boolean foundSystemInterface = false;
                for ( Class<?> aClass : cmdClass.getInterfaces() ) {
                    if ( SystemCommand.class.isAssignableFrom( aClass ) ) {
                        foundSystemInterface = true;
                        break;
                    }
                }

                // Check for combo command (player + system) and build / register it
                if ( Command.class.isAssignableFrom( cmdClass.getSuperclass() ) ) {
                    Class<? extends Command> nonSystem = (Class<? extends Command>) cmdClass;
                    commandObject = nonSystem.getConstructor().newInstance();
                    register( null, (Command) commandObject );
                } else if ( foundSystemInterface ) {
                    commandObject = cmdClass.getConstructor().newInstance();
                }

                // Check for system command interface register
                if ( foundSystemInterface ) {
                    registerSystem( (SystemCommand) commandObject );
                }
            }
        } catch ( InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e ) {
            e.printStackTrace();
        }
    }

    /**
     * Execute a system command
     *
     * @param line which the user has put in
     */
    public void executeSystem( String line ) {
        String[] split = line.split( " " );
        if ( split.length > 0 ) {
            SystemCommand command = this.systemCommands.get( split[0] );
            if ( command != null ) {
                String[] args;
                if ( split.length > 1 ) {
                    args = Arrays.copyOf( split, split.length - 1 );
                } else {
                    args = new String[0];
                }

                command.execute( args );
            }
        }
    }

    /**
     * Get suggestions for completion
     *
     * @param line input from the user (until now)
     * @return list of suggestions
     */
    public List<String> completeSystem( String line ) {
        String[] split = line.split( " " );
        if ( split.length > 0 ) {
            SystemCommand command = this.systemCommands.get( split[0] );
            if ( command != null ) {
                String[] args;
                if ( split.length > 1 ) {
                    args = Arrays.copyOf( split, split.length - 1 );
                } else {
                    args = new String[0];
                }

                return command.complete( args );
            } else {
                List<String> commandNames = new ArrayList<>();
                for ( String s : this.systemCommands.keySet() ) {
                    if ( s.startsWith( split[0] ) ) {
                        commandNames.add( s );
                    }
                }
                Collections.sort( commandNames );
                return commandNames;
            }
        } else {
            List<String> commandNames = new ArrayList<>();
            commandNames.addAll( this.systemCommands.keySet() );
            Collections.sort( commandNames );
            return commandNames;
        }
    }

    private void registerSystem( SystemCommand command ) {
        // System commands need to be static (@Name)
        if ( command.getClass().isAnnotationPresent( Name.class ) ) {
            String name = command.getClass().getAnnotation( Name.class ).value();
            this.systemCommands.put( name, command );
        }
    }

    public void register( Plugin plugin, Command commandBuilder ) {
        // Check if command is complete
        if ( commandBuilder.getName() == null ||
            commandBuilder.getDescription() == null ) {
            throw new IllegalStateException( "Name or Description can't be null" );
        }

        this.internalRegister( plugin, commandBuilder.getName(), commandBuilder );

        // TODO: Remove once aliases are fixed in 1.2
        if ( commandBuilder.getAlias() != null ) {
            for ( String s : commandBuilder.getAlias() ) {
                this.internalRegister( plugin, s, commandBuilder );
            }
        }
    }

    private void internalRegister( Plugin plugin, String name, Command commandBuilder ) {
        // Check for name collision
        CommandHolder holder = this.commands.get( name );
        if ( holder != null ) {
            // Remap the old command to its fallback
            Plugin originalPlugin = this.commandPlugins.remove( name );
            String cmdName;
            if ( originalPlugin != null ) {
                cmdName = originalPlugin.getName() + ":" + name;
            } else {
                cmdName = "gomint:" + name;
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
            name,
            commandBuilder.getDescription(),
            commandBuilder.getAlias(),
            CommandPermission.NORMAL,
            commandBuilder.getPermission(),
            commandBuilder,
            commandBuilder.getOverload() );

        // Store the command for usage
        this.commands.put( name, holder );
        if ( plugin != null ) {
            this.commandPlugins.put( name, plugin );
        }

        // Check for sub command
        if ( holder.getName().contains( " " ) ) {
            String[] split = name.split( " " );

            // We only support one deep sub commands. For the rest using the CommandValidator is recommended
            if ( split.length == 2 ) {
                SubCommand subCommand = this.subCommands.computeIfAbsent( split[0], new Function<String, SubCommand>() {
                    @Override
                    public SubCommand apply( String s ) {
                        return new SubCommand( plugin, s );
                    }
                } );

                subCommand.addCommand( plugin, split[1], holder );
            }
        }
    }

    public PacketAvailableCommands createPacket( EntityPlayer player ) {
        List<CommandHolder> holders = new ArrayList<>();

        // Sub commands
        for ( SubCommand subCommand : this.subCommands.values() ) {
            // Create needed holder
            CommandHolder holder = subCommand.createHolder( player );
            if ( holder != null ) {
                holders.add( holder );
            }
        }

        // Normal commands
        for ( CommandHolder holder : this.commands.values() ) {
            if ( !holder.getName().contains( " " ) &&
                ( holder.getPermission() == null ||
                player.hasPermission( holder.getPermission() ) ) ) {
                holders.add( holder );
            }
        }

        for ( CommandHolder holder : holders ) {
            LOGGER.debug( "Planning to send " + holder.getName() + " to " + player.getName() );
        }

        CommandPreprocessor preprocessor = new CommandPreprocessor( holders );
        return preprocessor.getCommandsPacket();
    }

    public Collection<CommandHolder> getCommands() {
        return commands.values();
    }

}
