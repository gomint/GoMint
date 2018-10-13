package io.gomint.event.plugin;

import io.gomint.plugin.Plugin;
import lombok.ToString;

/**
 * @author theminecoder
 * @version 1.0
 */
@ToString( callSuper = true )
public class PluginInstallEvent extends PluginEvent {

    public PluginInstallEvent( Plugin plugin ) {
        super(plugin);
    }

}
