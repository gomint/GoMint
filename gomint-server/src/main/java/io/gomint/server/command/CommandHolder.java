package io.gomint.server.command;

import io.gomint.command.Command;
import io.gomint.command.CommandOverload;
import io.gomint.server.entity.CommandPermission;

import java.util.List;
import java.util.Set;

/**
 * @author geNAZt
 * @version 1.0
 */
public class CommandHolder {

    private final String name;
    private final String description;
    private final Set<String> alias;

    private final CommandPermission commandPermission;
    private final String permission;
    private final boolean permissionDefault;
    private final Command executor;
    private final List<CommandOverload> overload;

    public CommandHolder(String name, String description, Set<String> alias, CommandPermission commandPermission, String permission, boolean permissionDefault, Command executor, List<CommandOverload> overload) {
        this.name = name;
        this.description = description;
        this.alias = alias;
        this.commandPermission = commandPermission;
        this.permission = permission;
        this.permissionDefault = permissionDefault;
        this.executor = executor;
        this.overload = overload;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Set<String> getAlias() {
        return alias;
    }

    public CommandPermission getCommandPermission() {
        return commandPermission;
    }

    public String getPermission() {
        return permission;
    }

    public boolean isPermissionDefault() {
        return permissionDefault;
    }

    public Command getExecutor() {
        return executor;
    }

    public List<CommandOverload> getOverload() {
        return overload;
    }
}
