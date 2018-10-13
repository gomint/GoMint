package io.gomint.event.plugin;

import io.gomint.event.Event;
import io.gomint.plugin.Plugin;
import lombok.ToString;

/**
 * @author theminecoder
 * @version 1.0
 */
@ToString( callSuper = true )
public class PluginEvent extends Event {

    private Plugin plugin;

    public PluginEvent( Plugin plugin ) {
        this.plugin = plugin;
    }

    public Plugin getPlugin() {
        return plugin;
    }
}
