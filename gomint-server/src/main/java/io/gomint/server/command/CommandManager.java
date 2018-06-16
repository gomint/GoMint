package io.gomint.server.command;

import io.gomint.ChatColor;
import io.gomint.command.*;
import io.gomint.command.annotation.Name;
import io.gomint.plugin.Plugin;
import io.gomint.server.command.internal.ListCommand;
import io.gomint.server.command.internal.StopCommand;
import io.gomint.server.command.internal.TPCommand;
import io.gomint.server.entity.CommandPermission;
import io.gomint.server.entity.EntityPlayer;
import io.gomint.server.network.packet.PacketAvailableCommands;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    private Map<String, SubCommand> subCommands = new HashMap<>();

    /**
     * Create a new command manager
     */
    public CommandManager() {
        // Register all internal commands
        try {
            for ( Class cmdClass : new Class[]{
                ListCommand.class,
                StopCommand.class,
                TPCommand.class
            } ) {
                // Check for system only commands
                Object commandObject = null;

                // Check for combo command (player + system) and build / register it
                if ( Command.class.isAssignableFrom( cmdClass.getSuperclass() ) ) {
                    Class<? extends Command> nonSystem = (Class<? extends Command>) cmdClass;
                    commandObject = nonSystem.getConstructor().newInstance();
                    register( null, (Command) commandObject );
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
        ConsoleCommandSender consoleCommandSender = new ConsoleCommandSender( line );
        CommandOutput output = this.dispatchCommand( consoleCommandSender, "/" + line );
        if ( output != null ) {
            for ( CommandOutputMessage message : output.getMessages() ) {
                if ( message.isSuccess() ) {
                    consoleCommandSender.sendMessage( CommandOutputParser.parse( message.getFormat(), message.getParameters() ) );
                } else {
                    consoleCommandSender.sendMessage( ChatColor.RED + CommandOutputParser.parse( message.getFormat(), message.getParameters() ) );
                }
            }
        }
    }

    /**
     * Dispatch a command
     *
     * @param sender  of the command
     * @param command which should be executed
     * @return command output
     */
    public CommandOutput dispatchCommand( CommandSender sender, String command ) {
        // Search for correct command holder
        String[] commandParts = command.substring( 1 ).split( " " );
        int consumed = 0;

        StringBuilder commandName = new StringBuilder( commandParts[consumed] );

        CommandHolder selected = null;
        while ( selected == null ) {
            for ( CommandHolder commandHolder : this.getCommands() ) {
                if ( commandName.toString().equalsIgnoreCase( commandHolder.getName() ) ) {
                    selected = commandHolder;
                    break;
                }
            }

            consumed++;
            if ( selected == null ) {
                if ( commandParts.length == consumed ) {
                    break;
                }

                commandName.append( " " ).append( commandParts[consumed] );
            }
        }

        // Check if we selected a command
        if ( selected == null ) {
            // Send CommandOutput with failure
            return new CommandOutput().fail( "Command for input '%%s' could not be found", command );
        } else {
            // Check for permission
            if ( selected.getPermission() != null && !sender.hasPermission( selected.getPermission() ) ) {
                return new CommandOutput().fail( "No permission for this command" );
            } else {
                // Now we need to parse all additional parameters
                String[] params;
                if ( commandParts.length > consumed ) {
                    params = new String[commandParts.length - consumed];
                    System.arraycopy( commandParts, consumed, params, 0, commandParts.length - consumed );
                } else {
                    params = new String[0];
                }

                if ( selected.getOverload() != null && params.length > 0 ) {
                    List<CommandCanidate> commandCanidates = new ArrayList<>();
                    for ( CommandOverload overload : selected.getOverload() ) {
                        Iterator<String> paramIterator = Arrays.asList( params ).iterator();

                        if ( !paramIterator.hasNext() && overload.getParameters() == null ) {
                            commandCanidates.add( new CommandCanidate( overload, new HashMap<>(), true, true ) );
                        } else {
                            Map<String, Object> commandInput = new HashMap<>();

                            boolean completed = true;
                            boolean completedOptionals = true;

                            for ( Map.Entry<String, ParamValidator> entry : overload.getParameters().entrySet() ) {
                                List<String> input = new ArrayList<>();
                                ParamValidator validator = entry.getValue();

                                if ( validator.consumesParts() > 0 ) {
                                    for ( int i = 0; i < validator.consumesParts(); i++ ) {
                                        if ( !paramIterator.hasNext() ) {
                                            if ( !validator.isOptional() ) {
                                                completed = false;
                                                break;
                                            } else {
                                                completedOptionals = false;
                                            }
                                        } else {
                                            input.add( paramIterator.next() );
                                        }
                                    }
                                } else {
                                    // Consume as much as possible for thing like TEXT, RAWTEXT
                                    while ( paramIterator.hasNext() ) {
                                        input.add( paramIterator.next() );
                                    }
                                }

                                if ( input.size() == validator.consumesParts() || validator.consumesParts() < 0 ) {
                                    Object result = validator.validate( input, sender );
                                    if ( result == null ) {
                                        completed = false;
                                    }

                                    commandInput.put( entry.getKey(), result );
                                }
                            }

                            if ( completed ) {
                                commandCanidates.add( new CommandCanidate( overload, commandInput, completedOptionals, !paramIterator.hasNext() && completedOptionals ) );
                            }
                        }
                    }

                    if ( !commandCanidates.isEmpty() ) {
                        // Select best canidate
                        commandCanidates.sort( ( o1, o2 ) -> {
                            if ( o1.isReadCompleted() && !o2.isReadCompleted() ) {
                                return -1;
                            } else if ( !o1.isReadCompleted() && o2.isReadCompleted() ) {
                                return 1;
                            }

                            return 0;
                        } );

                        CommandCanidate canidate = commandCanidates.get( 0 );
                        return tryCommandDispatch( sender, selected, canidate.getArguments() );
                    }

                    return new CommandOutput().fail( "Command for input '%%s' could not be found", command );
                } else {
                    return tryCommandDispatch( sender, selected, new HashMap<>() );
                }
            }
        }
    }

    private CommandOutput tryCommandDispatch( CommandSender sender, CommandHolder command, Map<String, Object> arguments ) {
        // CHECKSTYLE:OFF
        try {
            return command.getExecutor().execute( sender, command.getName(), arguments );
        } catch ( Exception e ) {
            LOGGER.warn( "Command '{}' failed", command.getName(), e );
            return new CommandOutput().fail( "Command has thrown an error. Please check the logs" );
        }
        // CHECKSTYLE:ON
    }

    /**
     * Get suggestions for completion
     *
     * @param line input from the user (until now)
     * @return list of suggestions
     */
    public List<String> completeSystem( String line ) {
        // Search for correct command holder
        String[] commandParts = line.split( " " );
        int consumed = 0;

        StringBuilder commandName = new StringBuilder( commandParts[consumed] );

        CommandHolder selected = null;
        while ( selected == null ) {
            for ( CommandHolder commandHolder : this.commands.values() ) {
                if ( commandName.toString().equalsIgnoreCase( commandHolder.getName() ) ) {
                    selected = commandHolder;
                    break;
                }
            }

            consumed++;
            if ( selected == null ) {
                if ( commandParts.length == consumed ) {
                    break;
                }

                commandName.append( " " ).append( commandParts[consumed] );
            }
        }

        if ( selected == null ) {
            // Check for commands which start with the input
            if ( line.contains( " " ) ) {
                return Collections.singletonList( "No command found for input" );
            }

            List<String> commandNames = new ArrayList<>();
            for ( CommandHolder commandHolder : this.commands.values() ) {
                if ( commandHolder.getName().startsWith( line ) ) {
                    commandNames.add( commandHolder.getName() + " - " + commandHolder.getDescription() );
                }
            }

            return commandNames;
        }

        List<String> commandNames = new ArrayList<>();
        for ( CommandOverload overload : selected.getOverload() ) {
            StringBuilder help = new StringBuilder( selected.getName() ).append( " " );
            if ( overload.getParameters() != null ) {
                for ( Map.Entry<String, ParamValidator> entry : overload.getParameters().entrySet() ) {
                    help.append( entry.getKey() ).append( entry.getValue().isOptional() ? "<" : " [" ).append( entry.getValue().getHelpText() ).append( entry.getValue().isOptional() ? ">" : "]" ).append( " " );
                }
            }

            commandNames.add( help.append( "- " ).append( selected.getDescription() ).toString() );
        }

        return commandNames;
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

        // NormalGenerator commands
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
