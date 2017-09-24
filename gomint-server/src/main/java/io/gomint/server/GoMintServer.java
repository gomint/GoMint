/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server;

import io.gomint.GoMint;
import io.gomint.GoMintInstanceHolder;
import io.gomint.entity.Player;
import io.gomint.inventory.item.ItemStack;
import io.gomint.jraknet.PacketBuffer;
import io.gomint.plugin.StartupPriority;
import io.gomint.server.assets.AssetsLibrary;
import io.gomint.server.config.ServerConfig;
import io.gomint.server.crafting.Recipe;
import io.gomint.server.crafting.RecipeManager;
import io.gomint.server.entity.EntityCow;
import io.gomint.server.inventory.CreativeInventory;
import io.gomint.server.inventory.InventoryHolder;
import io.gomint.server.inventory.MaterialMagicNumbers;
import io.gomint.server.inventory.item.Items;
import io.gomint.server.network.EncryptionKeyFactory;
import io.gomint.server.network.NetworkManager;
import io.gomint.server.network.Protocol;
import io.gomint.server.plugin.SimplePluginManager;
import io.gomint.server.scheduler.SyncTaskManager;
import io.gomint.server.util.DumpUtil;
import io.gomint.server.world.WorldAdapter;
import io.gomint.server.world.WorldManager;
import io.gomint.world.World;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;

/**
 * @author BlackyPaw
 * @author geNAZt
 * @version 1.1
 */
public class GoMintServer implements GoMint, InventoryHolder {

    private final Logger logger = LoggerFactory.getLogger( GoMintServer.class );

    // Configuration
    @Getter
    private ServerConfig serverConfig;

    // Networking
    @Getter
    private EncryptionKeyFactory encryptionKeyFactory;
    private NetworkManager networkManager;

    // World Management
    private WorldManager worldManager;

    // Game Information
    private RecipeManager recipeManager;
    private CreativeInventory creativeInventory;

    // Plugin Management
    @Getter
    private SimplePluginManager pluginManager;

    // Task Scheduling
    @Getter
    private SyncTaskManager syncTaskManager;
    private AtomicBoolean running = new AtomicBoolean( true );
    @Getter
    private ExecutorService executorService;
    @Getter
    private ThreadFactory threadFactory;

    /**
     * Starts the GoMint server
     *
     * @param args which should have been given over from the static Bootstrap
     */
    public GoMintServer( String[] args ) {
        GoMintInstanceHolder.setInstance( this );
        logger.info( "Starting " + getVersion() );
        Thread.currentThread().setName( "GoMint Main Thread" );

        // ------------------------------------ //
        // Executor Initialization
        // ------------------------------------ //
        this.threadFactory = new ThreadFactory() {
            private AtomicLong counter = new AtomicLong( 0 );

            @Override
            public Thread newThread( Runnable r ) {
                Thread thread = Executors.defaultThreadFactory().newThread( r );
                thread.setName( "GoMint Thread #" + counter.getAndIncrement() );
                return thread;
            }
        };

        this.executorService = new ThreadPoolExecutor( 0, 512, 60L, TimeUnit.SECONDS, new SynchronousQueue<>(), this.threadFactory );

        // ------------------------------------ //
        // Configuration Initialization
        // ------------------------------------ //
        this.loadConfig();

        // Calculate the nanoseconds we need for the tick loop
        long skipNanos = TimeUnit.SECONDS.toNanos( 1 ) / this.getServerConfig().getTargetTPS();
        logger.debug( "Setting skipNanos to: " + skipNanos );

        // ------------------------------------ //
        // Start of encryption helpers
        // ------------------------------------ //
        this.encryptionKeyFactory = new EncryptionKeyFactory( this );

        // ------------------------------------ //
        // Scheduler + PluginManager Initialization
        // ------------------------------------ //
        this.syncTaskManager = new SyncTaskManager( this, skipNanos );

        this.pluginManager = new SimplePluginManager( this );
        this.pluginManager.detectPlugins();
        this.pluginManager.loadPlugins( StartupPriority.STARTUP );

        // ------------------------------------ //
        // Pre World Initialization
        // ------------------------------------ //
        // Load assets from file:
        this.logger.info( "Loading assets library..." );
        AssetsLibrary assetsLibrary = new AssetsLibrary();
        try {
            assetsLibrary.load( this.getClass().getResourceAsStream( "/assets.dat" ) );
        } catch ( IOException e ) {
            this.logger.error( "Failed to load assets library", e );
            return;
        }

        this.logger.info( "Initializing recipes..." );
        this.recipeManager = new RecipeManager( this );

        // Add all recipes from asset library:
        for ( Recipe recipe : assetsLibrary.getRecipes() ) {
            this.recipeManager.registerRecipe( recipe );
        }

        this.creativeInventory = new CreativeInventory( this );

        // ------------------------------------ //
        // World Initialization
        // ------------------------------------ //
        this.worldManager = new WorldManager( this );
        // CHECKSTYLE:OFF
        try {
            this.worldManager.loadWorld( this.serverConfig.getWorld() );
        } catch ( Exception e ) {
            this.logger.error( "Failed to load default world", e );
        }
        // CHECKSTYLE:ON

        // ------------------------------------ //
        // Load plugins with StartupPriority LOAD
        // ------------------------------------ //
        this.pluginManager.loadPlugins( StartupPriority.LOAD );
        this.pluginManager.installPlugins();

        // ------------------------------------ //
        // Networking Initialization
        // ------------------------------------ //
        this.networkManager = new NetworkManager( this );
        if ( !this.initNetworking() ) return;
        setMotd( this.getServerConfig().getMotd() );

        // ------------------------------------ //
        // Main Loop
        // ------------------------------------ //

        // Spawn one cow for AI testing
        // EntityCow cow = new EntityCow( this.worldManager.getWorld( "world" ) );
        // this.worldManager.getWorld( "world" ).spawnEntityAt( cow, this.worldManager.getWorld( "world" ).getSpawnLocation() );

        // Tick loop
        float lastTickTime = Float.MIN_NORMAL;
        ReentrantLock tickLock = new ReentrantLock( true );
        Condition tickCondition = tickLock.newCondition();

        while ( this.running.get() ) {
            tickLock.lock();
            try {
                long start = System.nanoTime();

                // Tick all major subsystems:
                long currentMillis = System.currentTimeMillis();

                // Tick networking at every tick
                this.networkManager.update( currentMillis, lastTickTime );

                this.syncTaskManager.update( currentMillis, lastTickTime );
                this.worldManager.update( currentMillis, lastTickTime );

                long diff = System.nanoTime() - start;
                if ( diff < skipNanos ) {
                    tickCondition.await( skipNanos - diff, TimeUnit.NANOSECONDS );
                    lastTickTime = (float) skipNanos / 1000000000.0F;
                } else {
                    lastTickTime = (float) diff / 1000000000.0F;
                    this.logger.warn( "Running behind: " + ( 1 / lastTickTime ) + " / " + ( 1 / ( skipNanos / 1000000000.0F ) ) + " tps" );
                }
            } catch ( InterruptedException e ) {
                // Ignored ._.
            } finally {
                tickLock.unlock();
            }
        }

        // Safe shutdown
        this.networkManager.close();
        this.pluginManager.close();
        this.worldManager.close();

        this.executorService.shutdown();

        try {
            this.executorService.awaitTermination( 5, TimeUnit.SECONDS );
            this.executorService.shutdownNow();
        } catch ( InterruptedException e ) {
            e.printStackTrace();
        }
    }

    private boolean initNetworking() {
        try {
            this.networkManager.initialize( this.serverConfig.getMaxPlayers(), this.serverConfig.getListener().getIp(), this.serverConfig.getListener().getPort() );

            if ( this.serverConfig.isEnablePacketDumping() ) {
                File dumpDirectory = new File( this.serverConfig.getDumpDirectory() );
                if ( !dumpDirectory.exists() ) {
                    if ( !dumpDirectory.mkdirs() ) {
                        this.logger.error( "Failed to create dump directory; please double-check your filesystem permissions" );
                        return false;
                    }
                } else if ( !dumpDirectory.isDirectory() ) {
                    this.logger.error( "Dump directory path does not point to a valid directory" );
                    return false;
                }

                this.networkManager.setDumpingEnabled( true );
                this.networkManager.setDumpDirectory( dumpDirectory );
            }
        } catch ( SocketException e ) {
            this.logger.error( "Failed to initialize networking", e );
            return false;
        }

        return true;
    }

    public WorldAdapter getDefaultWorld() {
        return this.worldManager.getWorld( this.serverConfig.getWorld() );
    }

    public RecipeManager getRecipeManager() {
        return this.recipeManager;
    }

    private void loadConfig() {
        this.serverConfig = new ServerConfig();

        try {
            this.serverConfig.initialize( new File( "server.cfg" ) );
        } catch ( IOException e ) {
            logger.error( "server.cfg is corrupted: ", e );
            System.exit( -1 );
        }

        try ( FileWriter fileWriter = new FileWriter( new File( "server.cfg" ) ) ) {
            this.serverConfig.write( fileWriter );
        } catch ( IOException e ) {
            logger.warn( "Could not save server.cfg: ", e );
        }
    }

    @Override
    public String getMotd() {
        return this.networkManager.getMotd();
    }

    @Override
    public void setMotd( String motd ) {
        this.networkManager.setMotd( motd );
    }

    @Override
    public World getWorld( String name ) {
        World world = this.worldManager.getWorld( name );
        if ( world == null ) {
            // Try to load the world

            // CHECKSTYLE:OFF
            try {
                return this.worldManager.loadWorld( name );
            } catch ( Exception e ) {
                logger.warn( "Failed to load world: " + name, e );
                return null;
            }
            // CHECKSTYLE:ON
        }

        return world;
    }

    @Override
    public <T extends ItemStack> T createItemStack( Class<T> itemClass, int amount ) {
        return Items.create( itemClass, (byte) amount );
    }

    /**
     * Nice shutdown pls
     */
    public void shutdown() {
        this.running.set( false );
    }

    /**
     * Get the current version string
     *
     * @return the version of gomint
     */
    public String getVersion() {
        return "GoMint 1.0.0 (MC:PE " + Protocol.MINECRAFT_PE_NETWORK_VERSION + ")";
    }

    /**
     * Get all online players
     *
     * @return all online players
     */
    public Collection<Player> getPlayers() {
        List<Player> playerList = new ArrayList<>();

        worldManager.getWorlds().forEach( new Consumer<WorldAdapter>() {
            @Override
            public void accept( WorldAdapter worldAdapter ) {
                playerList.addAll( worldAdapter.getPlayers() );
            }
        } );

        return playerList;
    }

    /**
     * Get the amount of players currently online
     *
     * @return amount of players online
     */
    public int getAmountOfPlayers() {
        int amount = 0;

        for ( WorldAdapter worldAdapter : worldManager.getWorlds() ) {
            amount += worldAdapter.getAmountOfPlayers();
        }

        return amount;
    }

    public CreativeInventory getCreativeInventory() {
        return this.creativeInventory;
    }

}