package io.gomint.event.plugin;

import io.gomint.plugin.Plugin;

/**
 * @author theminecoder
 * @version 1.0
 */
public class PluginInstallEvent extends PluginEvent {

    public PluginInstallEvent( Plugin plugin ) {
        super(plugin);
    }

    @Override
    public String toString() {
        return super.toString();
    }

}
