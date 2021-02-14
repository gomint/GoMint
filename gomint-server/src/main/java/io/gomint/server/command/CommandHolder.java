package io.gomint.server.command;

import io.gomint.command.Command;
import io.gomint.command.CommandOverload;
import io.gomint.plugin.Plugin;
import io.gomint.server.entity.CommandPermission;

import java.util.List;
import java.util.Set;

/**
 * @author geNAZt
 * @version 1.0
 */
public class CommandHolder {

    private final Plugin plugin;
    private final String name;
    private final String description;
    private final Set<String> alias;
    private final boolean activeWorldsOnly;
    private final boolean console;

    private final CommandPermission commandPermission;
    private final String permission;
    private final boolean permissionDefault;
    private final Command executor;
    private final List<CommandOverload> overloads;

    public CommandHolder(Plugin plugin, String name, String description, Set<String> alias, boolean activeWorldsOnly,
                         boolean console, CommandPermission commandPermission, String permission,
                         boolean permissionDefault, Command executor, List<CommandOverload> overloads) {
        this.plugin = plugin;
        this.name = name;
        this.description = description;
        this.alias = alias;
        this.activeWorldsOnly = activeWorldsOnly;
        this.console = console;
        this.commandPermission = commandPermission;
        this.permission = permission;
        this.permissionDefault = permissionDefault;
        this.executor = executor;
        this.overloads = overloads;
    }

    public Plugin plugin() {
        return this.plugin;
    }

    public String name() {
        return this.name;
    }

    public String description() {
        return this.description;
    }

    public Set<String> alias() {
        return this.alias;
    }

    public boolean activeWorldsOnly() {
        return this.activeWorldsOnly;
    }

    public boolean console() {
        return this.console;
    }

    public CommandPermission commandPermission() {
        return this.commandPermission;
    }

    public String permission() {
        return this.permission;
    }

    public boolean isPermissionDefault() {
        return this.permissionDefault;
    }

    public Command executor() {
        return this.executor;
    }

    public List<CommandOverload> overloads() {
        return this.overloads;
    }

}
