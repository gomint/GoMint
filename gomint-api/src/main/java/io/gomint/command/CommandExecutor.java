package io.gomint.command;

import io.gomint.entity.Player;

import java.util.Map;

/**
 * @author geNAZt
 * @version 1.0
 */
public abstract class CommandExecutor {

    public abstract CommandOutput execute( Player player, Map<String, Object> arguments );

}
