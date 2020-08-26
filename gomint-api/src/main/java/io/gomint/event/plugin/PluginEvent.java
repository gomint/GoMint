package io.gomint.event.plugin;

import io.gomint.event.Event;
import io.gomint.plugin.Plugin;

import java.util.Objects;

/**
 * @author theminecoder
 * @version 1.0
 * @stability 3
 */
public class PluginEvent extends Event {

    private Plugin plugin;

    public PluginEvent( Plugin plugin ) {
        this.plugin = plugin;
    }

    public Plugin getPlugin() {
        return plugin;
    }

    @Override
    public String toString() {
        return "PluginEvent{" +
            "plugin=" + plugin +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PluginEvent that = (PluginEvent) o;
        return Objects.equals(plugin, that.plugin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(plugin);
    }

}
