package io.gomint.server.command;

import io.gomint.ChatColor;
import io.gomint.command.Command;
import io.gomint.command.CommandOutput;
import io.gomint.command.CommandOutputMessage;
import io.gomint.command.CommandOverload;
import io.gomint.command.CommandSender;
import io.gomint.command.ParamValidator;
import io.gomint.command.PlayerCommandSender;
import io.gomint.plugin.Plugin;
import io.gomint.server.command.cli.MemoryDumpCommand;
import io.gomint.server.command.gomint.KickCommand;
import io.gomint.server.command.gomint.PluginsCommand;
import io.gomint.server.command.gomint.StopCommand;
import io.gomint.server.command.gomint.VersionCommand;
import io.gomint.server.command.vanilla.DeopCommand;
import io.gomint.server.command.vanilla.DifficultyCommand;
import io.gomint.server.command.vanilla.GamemodeCommand;
import io.gomint.server.command.vanilla.KillCommand;
import io.gomint.server.command.vanilla.ListCommand;
import io.gomint.server.command.vanilla.MeCommand;
import io.gomint.server.command.vanilla.OpCommand;
import io.gomint.server.command.vanilla.SayCommand;
import io.gomint.server.command.vanilla.SetWorldSpawnCommand;
import io.gomint.server.command.vanilla.SpawnPointCommand;
import io.gomint.server.command.vanilla.TPCommand;
import io.gomint.server.command.vanilla.TellCommand;
import io.gomint.server.command.vanilla.TimeSetCommand;
import io.gomint.server.entity.CommandPermission;
import io.gomint.server.entity.EntityPlayer;
import io.gomint.server.network.packet.PacketAvailableCommands;
import io.gomint.server.world.WorldAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import javax.annotation.Nullable;

/**
 * @author geNAZt
 * @version 1.0
 */
public class CommandManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommandManager.class);
    private static final Constructor<CommandOutput> COMMAND_OUTPUT_CONSTRUCTOR;

    static {
        try {
            COMMAND_OUTPUT_CONSTRUCTOR = CommandOutput.class.getDeclaredConstructor(Consumer.class);
            COMMAND_OUTPUT_CONSTRUCTOR.setAccessible(true);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    public static CommandOutput newCommandOutput(Consumer<CommandOutput> outputConsumer) {
        try {
            return COMMAND_OUTPUT_CONSTRUCTOR.newInstance(outputConsumer);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    private Map<String, CommandHolder> commands = new HashMap<>();
    private Map<String, Plugin> commandPlugins = new HashMap<>();
    private Map<String, SubCommand> subCommands = new HashMap<>();

    /**
     * Create a new command manager
     */
    public CommandManager() {
        // Register all internal commands
        try {
            for (Class<? extends Command> cmdClass : new Class[]{
                // Vanilla
                DeopCommand.class,
                DifficultyCommand.class,
                ListCommand.class,
                OpCommand.class,
                GamemodeCommand.class,
                PluginsCommand.class,
                MeCommand.class,
                SayCommand.class,
                SetWorldSpawnCommand.class,
                SpawnPointCommand.class,
                TPCommand.class,
                TellCommand.class,
                KillCommand.class,
                TimeSetCommand.class,

                // GoMint
                KickCommand.class,
                StopCommand.class,
                VersionCommand.class,

                // CLI
                MemoryDumpCommand.class,
            }) {
                // Check for system only commands
                Command commandObject;

                // Check for combo command (player + system) and build / register it
                if (Command.class.isAssignableFrom(cmdClass.getSuperclass())) {
                    commandObject = cmdClass.getConstructor().newInstance();
                    register(null, commandObject);
                }
            }
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * Execute a system command
     *
     * @param line which the user has put in (without leading {@code /})
     */
    public void executeSystem(String line) {
        ConsoleCommandSender consoleCommandSender = new ConsoleCommandSender(line);
        this.dispatchCommand(consoleCommandSender, "/" + line, output -> {
            if (output != null) {
                for (CommandOutputMessage message : output.messages()) {
                    if (message.success()) {
                        consoleCommandSender.sendMessage(CommandOutputParser.parse(message.format(), message.parameters()));
                    } else {
                        consoleCommandSender.sendMessage(ChatColor.RED + CommandOutputParser.parse(message.format(), message.parameters()));
                    }
                }
            }
        });
    }

    /**
     * Dispatch a command, will be executed in current thread for console command sender, otherwise will be scheduled in
     * world's thread, if the current thread is not the world's thread.
     *
     * @param sender         of the command
     * @param command        which should be executed (includes leading {@code /})
     * @param outputConsumer the command output consumer, no guarantee is given in which thread it is executed
     * @return CommandManager for chaining
     */
    public CommandManager dispatchCommand(CommandSender<?> sender, String command, @Nullable Consumer<CommandOutput> outputConsumer) {
        CommandOutput output = newCommandOutput(outputConsumer);
        if (sender instanceof io.gomint.command.ConsoleCommandSender) {
            dispatchCommand0(sender, command, output);
        } else if (sender instanceof EntityPlayer) {
            WorldAdapter world = ((EntityPlayer) sender).world();
            if (world.mainThread()) {
                dispatchCommand0(sender, command, output);
            } else {
                world.syncScheduler().execute(() -> {
                    dispatchCommand0(sender, command, output);
                });
            }
        } else {
            throw new UnsupportedOperationException("Unsupported command sender " + sender.getClass().getName() + " - " + sender);
        }
        return this;
    }

    private void dispatchCommand0(CommandSender<?> sender, String command, CommandOutput output) {
        // Search for correct command holder
        String[] commandParts = command.substring(1).split(" ");
        int consumed = 0;

        StringBuilder commandName = new StringBuilder(commandParts[consumed]);

        CommandHolder selected = null;
        while (selected == null) {
            for (CommandHolder commandHolder : this.getCommands()) {
                String cmd = commandName.toString();
                if (cmd.equalsIgnoreCase(commandHolder.getName()) || (commandHolder.getAlias() != null && commandHolder.getAlias().contains(cmd))) {
                    selected = commandHolder;
                    break;
                }
            }

            consumed++;
            if (selected == null) {
                if (commandParts.length == consumed) {
                    break;
                }

                commandName.append(" ").append(commandParts[consumed]);
            }
        }

        // Check if we selected a command
        if (selected == null) {
            // Send CommandOutput with failure
            output.fail("Command for input '%%s' could not be found", command).markFinished();
        } else {
            // Check for world
            if (selected.activeWorldsOnly() && sender instanceof PlayerCommandSender) {
                if (!selected.plugin().activeInWorld(sender.world())) {
                    output.fail("Command for input '%%s' could not be found", command).markFinished();
                }
            }
            // Check if allowed for console
            if (!selected.console() && sender instanceof io.gomint.command.ConsoleCommandSender) {
                output.fail("Command for input '%%s' is not registered for console", command).markFinished();
            }
            // Check for permission
            if (selected.getPermission() != null && !sender.hasPermission(selected.getPermission())) {
                output.fail("No permission for this command").markFinished();
            } else {
                // Now we need to parse all additional parameters
                String[] params;
                if (commandParts.length > consumed) {
                    params = new String[commandParts.length - consumed];
                    System.arraycopy(commandParts, consumed, params, 0, commandParts.length - consumed);
                } else {
                    params = new String[0];
                }

                if (selected.getOverload() != null && params.length > 0) {
                    List<CommandCanidate> commandCanidates = new ArrayList<>();
                    for (CommandOverload overload : selected.getOverload()) {
                        if (overload.permission().isEmpty() || sender.hasPermission(overload.permission())) {
                            Iterator<String> paramIterator = Arrays.asList(params).iterator();

                            if (!paramIterator.hasNext() && overload.parameters() == null) {
                                commandCanidates.add(new CommandCanidate(overload, new HashMap<>(), true, true));
                            } else {
                                Map<String, Object> commandInput = new HashMap<>();

                                boolean completed = true;
                                boolean completedOptionals = true;

                                if (overload.parameters() != null) {
                                    for (Map.Entry<String, ParamValidator<?>> entry : overload.parameters().entrySet()) {
                                        ParamValidator<?> validator = entry.getValue();

                                        String forValidator = validator.consume(paramIterator);
                                        if (forValidator == null) {
                                            if (!validator.optional()) {
                                                completed = false;
                                                break;
                                            } else {
                                                completedOptionals = false;
                                            }
                                        }

                                        if (forValidator != null) {
                                            Object result = validator.validate(forValidator, sender);
                                            if (result == null) {
                                                completed = false;
                                            }

                                            commandInput.put(entry.getKey(), result);
                                        }
                                    }
                                }

                                if (completed) {
                                    commandCanidates.add(new CommandCanidate(overload, commandInput, completedOptionals, !paramIterator.hasNext() && completedOptionals));
                                }
                            }
                        }
                    }

                    if (!commandCanidates.isEmpty()) {
                        // Select best canidate
                        commandCanidates.sort((o1, o2) -> {
                            if (o1.isReadCompleted() && !o2.isReadCompleted()) {
                                return -1;
                            } else if (!o1.isReadCompleted() && o2.isReadCompleted()) {
                                return 1;
                            }

                            return 0;
                        });

                        CommandCanidate canidate = commandCanidates.get(0);
                        tryCommandDispatch(sender, selected, canidate.getArguments(), output);
                        return;
                    }

                    output.fail("Command for input '%%s' could not be found", command).markFinished();
                } else {
                    tryCommandDispatch(sender, selected, new HashMap<>(), output);
                }
            }
        }
    }

    private void tryCommandDispatch(CommandSender<?> sender, CommandHolder command, Map<String, Object> arguments, CommandOutput output) {
        // CHECKSTYLE:OFF
        try {
            command.getExecutor().execute(sender, command.getName(), arguments, output);
            if (!output.isAsync()) {
                output.markFinished();
            }
        } catch (Exception e) {
            LOGGER.warn("Command '{}' failed", command.getName(), e);
            output.fail(e).markFinished();
        }
        // CHECKSTYLE:ON
    }

    /**
     * Get suggestions for completion
     *
     * @param line input from the user (until now)
     * @return list of suggestions
     */
    public List<String> completeSystem(String line) {
        // Search for correct command holder
        String[] commandParts = line.split(" ");
        int consumed = 0;

        StringBuilder commandName = new StringBuilder(commandParts[consumed]);

        CommandHolder selected = null;
        while (selected == null) {
            for (CommandHolder commandHolder : this.commands.values()) {
                String cmd = commandName.toString();
                if (cmd.equalsIgnoreCase(commandHolder.getName()) || (commandHolder.getAlias() != null && commandHolder.getAlias().contains(cmd))) {
                    selected = commandHolder;
                    break;
                }
            }

            consumed++;
            if (selected == null) {
                if (commandParts.length == consumed) {
                    break;
                }

                commandName.append(" ").append(commandParts[consumed]);
            }
        }

        if (selected == null) {
            // Check for commands which start with the input
            if (line.contains(" ")) {
                return Collections.singletonList("No command found for input");
            }

            List<String> commandNames = new ArrayList<>();
            for (CommandHolder commandHolder : this.commands.values()) {
                if (commandHolder.getName().startsWith(line)) {
                    commandNames.add(commandHolder.getName() + " - " + commandHolder.getDescription());
                }
            }

            return commandNames;
        }

        List<String> commandNames = new ArrayList<>();
        for (CommandOverload overload : selected.getOverload()) {
            StringBuilder help = new StringBuilder(selected.getName()).append(" ");
            if (overload.parameters() != null) {
                for (Map.Entry<String, ParamValidator<?>> entry : overload.parameters().entrySet()) {
                    help.append(entry.getKey()).append(entry.getValue().optional() ? "<" : " [").append(entry.getValue().helpText()).append(entry.getValue().optional() ? ">" : "]").append(" ");
                }
            }

            commandNames.add(help.append("- ").append(selected.getDescription()).toString());
        }

        return commandNames;
    }

    public void register(Plugin plugin, Command commandBuilder) {
        // Check if command is complete
        if (commandBuilder.getName() == null ||
            commandBuilder.getDescription() == null) {
            throw new IllegalStateException("Name or Description can't be null");
        }

        this.internalRegister(plugin, commandBuilder.getName(), commandBuilder);
    }

    private void internalRegister(Plugin plugin, String name, Command commandBuilder) {
        // Check for name collision
        CommandHolder holder = this.commands.get(name);
        if (holder != null) {
            // Remap the old command to its fallback
            Plugin originalPlugin = this.commandPlugins.remove(name);
            String cmdName;
            if (originalPlugin != null) {
                cmdName = originalPlugin.name() + ":" + name;
            } else {
                cmdName = "gomint:" + name;
            }

            CommandHolder commandHolder = new CommandHolder(
                holder.plugin(),
                cmdName,
                holder.getDescription(),
                holder.getAlias(),
                holder.activeWorldsOnly(),
                holder.console(),
                holder.getCommandPermission(),
                holder.getPermission(),
                holder.isPermissionDefault(),
                holder.getExecutor(),
                holder.getOverload()
            );

            this.commands.put(cmdName, commandHolder);
            this.commandPlugins.put(cmdName, originalPlugin);
        }

        // Create a new holder
        holder = new CommandHolder(
            plugin,
            name,
            commandBuilder.getDescription(),
            commandBuilder.getAlias(),
            commandBuilder.activeWorldsOnly(),
            commandBuilder.console(),
            CommandPermission.NORMAL,
            commandBuilder.getPermission(),
            commandBuilder.isPermissionDefault(),
            commandBuilder,
            commandBuilder.getOverload());

        // Store the command for usage
        this.commands.put(name, holder);
        if (plugin != null) {
            this.commandPlugins.put(name, plugin);
        }

        // Check for sub command
        if (holder.getName().contains(" ")) {
            String[] split = name.split(" ");

            // We only support one deep sub commands. For the rest using the CommandValidator is recommended
            if (split.length == 2) {
                SubCommand subCommand = this.subCommands.computeIfAbsent(split[0], new Function<>() {
                    @Override
                    public SubCommand apply(String s) {
                        return new SubCommand(plugin, s);
                    }
                });

                subCommand.addCommand(plugin, split[1], holder);
            }
        }
    }

    public PacketAvailableCommands createPacket(EntityPlayer player) {
        List<CommandHolder> holders = new ArrayList<>();

        // Sub commands
        for (SubCommand subCommand : this.subCommands.values()) {
            // Create needed holder
            CommandHolder holder = subCommand.createHolder(player);
            if (holder != null) {
                holders.add(holder);
            }
        }

        // NormalGenerator commands
        for (CommandHolder holder : this.commands.values()) {
            if (!holder.getName().contains(" ") &&
                (holder.getPermission() == null || 
                    player.hasPermission(holder.getPermission(), holder.isPermissionDefault())) &&
                (!holder.activeWorldsOnly() || holder.plugin().activeInWorld(player.world()))) {
                holders.add(holder);
            }
        }

        CommandPreprocessor preprocessor = new CommandPreprocessor(player, holders);
        return preprocessor.getCommandsPacket();
    }

    public Collection<CommandHolder> getCommands() {
        return this.commands.values();
    }

}
