package io.gomint.plugin;

import io.gomint.plugin.listener.PlayerInteractListener;
import io.gomint.plugin.listener.PlayerJoinListener;
import io.gomint.plugin.listener.PlayerRespawnListener;

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
        registerListener(new PlayerInteractListener());
        registerListener(new PlayerRespawnListener());
    }

}
