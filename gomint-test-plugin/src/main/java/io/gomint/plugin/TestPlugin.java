package io.gomint.plugin;

import io.gomint.GoMint;
import io.gomint.plugin.listener.EntityDamageListener;
import io.gomint.plugin.listener.EntityDeathListener;
import io.gomint.plugin.listener.PlayerJoinListener;
import io.gomint.world.World;
import io.gomint.world.generator.CreateOptions;
import io.gomint.world.generator.integrated.NormalGenerator;

/**
 * @author geNAZt
 * @version 1.0
 */
@PluginName( "TestPlugin" )
@Version( major = 1, minor = 0 )
@Startup( StartupPriority.STARTUP )
public class TestPlugin extends Plugin {

    @Override
    public void onInstall() {
        // Generate two worlds (one in memory and one in anvil format)
        World world = GoMint.instance().createWorld( "test", new CreateOptions().generator( NormalGenerator.class ) );
        this.getLogger().warn( world.getHighestBlockAt( 150, 150 ).getLocation().toString() );

        // Register listener
        registerListener( new PlayerJoinListener( this ) );
        registerListener( new EntityDamageListener() );
        registerListener( new EntityDeathListener() );
    }

    @Override
    public void onUninstall() {

    }

}
