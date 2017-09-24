package io.gomint.plugin;

import io.gomint.plugin.listener.PlayerJoinListener;
import io.gomint.plugin.listener.PlayerMoveListener;

/**
 * @author geNAZt
 * @version 1.0
 */
@Name( "TestPlugin" )
@Version( major = 1, minor = 0 )
public class TestPlugin extends Plugin {

    @Override
    public void onStartup() {
        getLogger().info( "Started " + this.getName() + " " + this.getVersion() );
    }

    @Override
    public void onInstall() {
        registerListener( new PlayerMoveListener() );
        registerListener( new PlayerJoinListener() );
    }

    @Override
    public void onUninstall() {
        // This is like Bukkits onDisable();
    }

}
