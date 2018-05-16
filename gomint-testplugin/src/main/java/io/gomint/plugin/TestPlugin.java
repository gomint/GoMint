package io.gomint.plugin;

import io.gomint.GoMint;
import io.gomint.config.InvalidConfigurationException;
import io.gomint.plugin.config.Config;
import io.gomint.plugin.generator.PlotChunkGenerator;
import io.gomint.plugin.listener.BlockBreakListener;
import io.gomint.plugin.listener.PlayerJoinListener;
import io.gomint.plugin.listener.PlayerMoveListener;
import io.gomint.world.WorldType;
import io.gomint.world.generator.CreateOptions;
import lombok.Getter;

import java.io.File;

/**
 * @author geNAZt
 * @version 1.0
 */
@PluginName( "TestPlugin" )
@Version( major = 1, minor = 0 )
@Startup( StartupPriority.STARTUP )
public class TestPlugin extends Plugin {

    @Getter
    private Config config;

    @Override
    public void onStartup() {
        getLogger().info( "Started " + this.getName() + " " + this.getVersion() );

        // Check if the plugin folder is there
        File pluginDirectory = this.getDataFolder();
        if ( !pluginDirectory.exists() ) {
            pluginDirectory.mkdirs();
        }

        // Load config
        this.config = new Config();
        try {
            this.config.init( new File( pluginDirectory, "config.yml" ) );
        } catch ( InvalidConfigurationException e ) {
            throw new PluginLoadException( "Could not init config", e );
        }

        // Check if we need to test chunk generation
        if ( this.config.isEnableChunkGeneration() ) {
            GoMint.instance().createWorld( "test_plot", new CreateOptions().generator( PlotChunkGenerator.class ).worldType( WorldType.GOMINT ) );
        }
    }

    @Override
    public void onInstall() {
        /*EntityHuman floatingText = EntityHuman.create();
        floatingText.setSkin( PlayerSkin.empty() );
        floatingText.setScale( 0f );
        floatingText.setTicking( false );
        floatingText.setNameTag( "Test123" );
        floatingText.spawn( new Location( GoMint.instance().getWorld( "test_plot" ), 12, 83, 12 ) );*/


        // We want to shutdown because of some error
        //this.getServer().shutdown();

        // Register listener
        registerListener( new PlayerMoveListener( this ) );
        registerListener( new PlayerJoinListener( this ) );
        registerListener( new BlockBreakListener() );
    }

    @Override
    public void onUninstall() {

    }

}
