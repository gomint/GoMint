package io.gomint.server.command;

import io.gomint.command.CommandExecutor;
import io.gomint.command.CommandOverload;
import io.gomint.command.CommandPermission;
import io.gomint.command.ParamValidator;
import io.gomint.server.entity.CommandPermissionMagicNumbers;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author geNAZt
 */
@AllArgsConstructor
@Getter
public class CommandHolder {

    private String name;
    private String description;
    private Set<String> alias;

    private CommandPermissionMagicNumbers commandPermission;
    private String permission;
    private CommandExecutor executor;
    private List<CommandOverload> overload;

}
