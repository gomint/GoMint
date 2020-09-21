package io.gomint.server.command.gomint;

import io.gomint.GoMint;
import io.gomint.command.Command;
import io.gomint.command.CommandOutput;
import io.gomint.command.CommandSender;
import io.gomint.command.annotation.Description;
import io.gomint.command.annotation.Name;
import io.gomint.command.annotation.Permission;
import io.gomint.plugin.Plugin;
import io.gomint.server.util.StringUtil;

import java.util.Map;

/**
 * @author rjworks
 * @version 1.0
 */
@Name( "plugins" )
@Description( "View plugins enabled on this server." )
@Permission( "gomint.command.plugins" )
public class PluginsCommand extends Command {
    @Override
    public CommandOutput execute( CommandSender commandSender, String alias, Map< String, Object > arguments ) {
        Map< String, Plugin > plugins = GoMint.instance().getPluginManager().getPlugins();
        return new CommandOutput().success( String.join( ", ", plugins.keySet() ) );
    }
}
