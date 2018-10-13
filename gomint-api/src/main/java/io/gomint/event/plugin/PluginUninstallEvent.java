package io.gomint.event.plugin;

import io.gomint.plugin.Plugin;
import lombok.ToString;

/**
 * @author theminecoder
 * @version 1.0
 */
@ToString( callSuper = true )
public class PluginUninstallEvent extends PluginEvent {

    public PluginUninstallEvent(Plugin plugin ) {
        super(plugin);
    }

}
