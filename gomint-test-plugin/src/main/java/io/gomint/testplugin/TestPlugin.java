package io.gomint.testplugin;

import io.gomint.plugin.Plugin;
import io.gomint.plugin.PluginName;
import io.gomint.plugin.Startup;
import io.gomint.plugin.StartupPriority;
import io.gomint.plugin.Version;
import io.gomint.testplugin.listener.InventoryOpenListener;
import io.gomint.testplugin.listener.PlayerInteractListener;
import io.gomint.testplugin.listener.PlayerJoinListener;
import io.gomint.testplugin.listener.PlayerRespawnListener;

/**
 * @author geNAZt
 * @version 1.0
 */
@PluginName("TestPlugin")
@Version(major = 1, minor = 0)
@Startup(StartupPriority.STARTUP)
public class TestPlugin extends Plugin {

    @Override
    public void onInstall() {
        // Register listener
        registerListener(new PlayerJoinListener(this));
        registerListener(new InventoryOpenListener());
        registerListener(new PlayerRespawnListener());
    }

}
