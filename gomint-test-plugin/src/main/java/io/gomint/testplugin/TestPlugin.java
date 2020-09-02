package io.gomint.testplugin;

import io.gomint.entity.passive.EntitySheep;
import io.gomint.math.Location;
import io.gomint.testplugin.listener.PlayerInteractListener;
import io.gomint.testplugin.listener.PlayerJoinListener;
import io.gomint.testplugin.listener.PlayerRespawnListener;
import io.gomint.plugin.Plugin;
import io.gomint.plugin.Version;
import io.gomint.plugin.Startup;
import io.gomint.plugin.StartupPriority;
import io.gomint.plugin.PluginName;

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

        // Get world and spawn 5k sheeps in it
        Location location = this.getServer().getDefaultWorld().getSpawnLocation();
        for (int i = 0; i < 500; i++) {
            EntitySheep sheep = EntitySheep.create();
            sheep.spawn(location);
        }
    }

}
