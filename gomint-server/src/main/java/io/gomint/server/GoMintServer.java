/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server;

import com.google.common.reflect.ClassPath;
import io.gomint.GoMint;
import io.gomint.GoMintInstanceHolder;
import io.gomint.entity.Entity;
import io.gomint.entity.EntityPlayer;
import io.gomint.gui.ButtonList;
import io.gomint.gui.CustomForm;
import io.gomint.gui.Modal;
import io.gomint.inventory.item.ItemStack;
import io.gomint.permission.GroupManager;
import io.gomint.plugin.StartupPriority;
import io.gomint.server.assets.AssetsLibrary;
import io.gomint.server.config.ServerConfig;
import io.gomint.server.config.WorldConfig;
import io.gomint.server.crafting.Recipe;
import io.gomint.server.crafting.RecipeManager;
import io.gomint.server.entity.Entities;
import io.gomint.server.inventory.CreativeInventory;
import io.gomint.server.inventory.InventoryHolder;
import io.gomint.server.inventory.item.Items;
import io.gomint.server.logging.TerminalConsoleAppender;
import io.gomint.server.network.EncryptionKeyFactory;
import io.gomint.server.network.NetworkManager;
import io.gomint.server.network.Protocol;
import io.gomint.server.permission.PermissionGroupManager;
import io.gomint.server.plugin.SimplePluginManager;
import io.gomint.server.scheduler.SyncTaskManager;
import io.gomint.server.world.WorldAdapter;
import io.gomint.server.world.WorldManager;
import io.gomint.world.World;
import joptsimple.OptionSet;
import lombok.Getter;
import org.jline.reader.*;
import org.jline.terminal.Terminal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.SocketException;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author BlackyPaw
 * @author geNAZt
 * @version 1.1
 */
public class GoMintServer implements GoMint, InventoryHolder {

    private static final Logger LOGGER = LoggerFactory.getLogger( GoMintServer.class );
    @Getter private static ClassPath classPath;
    private static long mainThread;

    // Configuration
    @Getter
    private ServerConfig serverConfig;

    // Networking
    @Getter
    private EncryptionKeyFactory encryptionKeyFactory;
    private NetworkManager networkManager;

    // Player lookups
    @Getter
    private Map<UUID, EntityPlayer> playersByUUID = new ConcurrentHashMap<>();

    // World Management
    @Getter
    private WorldManager worldManager;

    // Game Information
    private RecipeManager recipeManager;
    private CreativeInventory creativeInventory;
    private PermissionGroupManager permissionGroupManager;

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
    private Thread readerThread;
    private long currentTickTime;

    // Additional informations for API usage
    private double tps;

    /**
     * Starts the GoMint server
     *
     * @param args which should have been given over from the static Bootstrap
     */
    public GoMintServer( OptionSet args ) {
        long start = System.currentTimeMillis();

        try {
            GoMintServer.classPath = ClassPath.from( ClassLoader.getSystemClassLoader() );
        } catch ( IOException e ) {
            GoMintServer.classPath = null;
            LOGGER.error( "Could no init classpath scanner: ", e );
            return;
        }

        GoMintServer.mainThread = Thread.currentThread().getId();
        GoMintInstanceHolder.setInstance( this );

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

        this.executorService = new ThreadPoolExecutor( 0, 512, 60L,
            TimeUnit.SECONDS, new SynchronousQueue<>(), this.threadFactory );

        // ------------------------------------ //
        // jLine setup
        // ------------------------------------ //
        BlockingQueue<String> inputLines = new LinkedBlockingQueue<>();

        LineReader reader = null;
        Terminal terminal = TerminalConsoleAppender.getTerminal();
        if ( terminal != null ) {
            reader = LineReaderBuilder.builder()
                .appName( "GoMint" )
                .terminal( terminal )
                .completer( new Completer() {
                    @Override
                    public void complete( LineReader lineReader, ParsedLine parsedLine, List<Candidate> list ) {
                        List<String> suggestions = pluginManager.getCommandManager().completeSystem( parsedLine.line() );
                        for ( String suggestion : suggestions ) {
                            list.add( new Candidate( suggestion ) );
                        }
                    }
                } )
                .build();

            reader.setKeyMap( "emacs" );

            TerminalConsoleAppender.setReader( reader );
        }

        // ------------------------------------ //
        // Setup jLine reader thread
        // ------------------------------------ //
        if ( reader != null ) {
            LineReader finalReader = reader;
            AtomicBoolean reading = new AtomicBoolean( false );
            this.readerThread = new Thread( () -> {
                String line;
                while ( running.get() ) {
                    // Read jLine
                    reading.set( true );
                    try {
                        line = finalReader.readLine( "\u001b[32;0mGoMint\u001b[39;0m> " );
                        inputLines.offer( line );
                    } catch ( UserInterruptException e ) {
                        GoMintServer.this.shutdown();
                    } catch ( EndOfFileException e ) {
                        e.printStackTrace();
                    }
                }
            } );
            this.readerThread.setName( "GoMint CLI reader" );
            this.readerThread.start();

            // Wait until we read
            while ( !reading.get() ) {
            }
        }

        LOGGER.info( "Starting " + getVersion() );
        Thread.currentThread().setName( "GoMint Main Thread" );

        // ------------------------------------ //
        // Configuration Initialization
        // ------------------------------------ //
        this.loadConfig();

        // Calculate the nanoseconds we need for the tick loop
        long skipNanos = TimeUnit.SECONDS.toNanos( 1 ) / this.getServerConfig().getTargetTPS();
        LOGGER.debug( "Setting skipNanos to: " + skipNanos );

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
        LOGGER.info( "Loading assets library..." );
        AssetsLibrary assetsLibrary = new AssetsLibrary();
        try {
            assetsLibrary.load( this.getClass().getResourceAsStream( "/assets.dat" ) );
        } catch ( IOException e ) {
            LOGGER.error( "Failed to load assets library", e );
            return;
        }

        LOGGER.info( "Initializing recipes..." );
        this.recipeManager = new RecipeManager( this );

        // Add all recipes from asset library:
        for ( Recipe recipe : assetsLibrary.getRecipes() ) {
            this.recipeManager.registerRecipe( recipe );
        }

        this.creativeInventory = new CreativeInventory( this );
        this.permissionGroupManager = new PermissionGroupManager();

        // ------------------------------------ //
        // World Initialization
        // ------------------------------------ //
        this.worldManager = new WorldManager( this );
        // CHECKSTYLE:OFF
        try {
            this.worldManager.loadWorld( this.serverConfig.getDefaultWorld() );
        } catch ( Exception e ) {
            LOGGER.error( "Failed to load default world", e );
        }
        // CHECKSTYLE:ON

        // ------------------------------------ //
        // Networking Initialization
        // ------------------------------------ //
        int port = args.has( "lp" ) ? (int) args.valueOf( "lp" ) : this.serverConfig.getListener().getPort();
        String host = args.has( "lh" ) ? (String) args.valueOf( "lh" ) : this.serverConfig.getListener().getIp();

        this.networkManager = new NetworkManager( this );
        if ( !this.initNetworking( host, port ) ) return;
        setMotd( this.getServerConfig().getMotd() );

        // ------------------------------------ //
        // Load plugins with StartupPriority LOAD
        // ------------------------------------ //
        this.pluginManager.loadPlugins( StartupPriority.LOAD );
        this.pluginManager.installPlugins();

        LOGGER.info( "Done in " + ( System.currentTimeMillis() - start ) + " ms" );

        // ------------------------------------ //
        // Main Loop
        // ------------------------------------ //

        // Tick loop
        float lastTickTime = Float.MIN_NORMAL;
        ReentrantLock tickLock = new ReentrantLock( true );
        Condition tickCondition = tickLock.newCondition();

        while ( this.running.get() ) {
            tickLock.lock();
            try {
                start = System.nanoTime();

                // Tick all major subsystems:
                this.currentTickTime = System.currentTimeMillis();

                // Drain input lines
                while ( inputLines.size() > 0 ) {
                    String line = inputLines.take();
                    this.pluginManager.getCommandManager().executeSystem( line );
                }

                // Tick networking at every tick
                this.networkManager.update( this.currentTickTime, lastTickTime );

                this.syncTaskManager.update( this.currentTickTime, lastTickTime );
                this.worldManager.update( this.currentTickTime, lastTickTime );
                this.permissionGroupManager.update( this.currentTickTime, lastTickTime );

                // Check if we got shutdown
                if ( !this.running.get() ) {
                    break;
                }

                long diff = System.nanoTime() - start;
                if ( diff < skipNanos ) {
                    tickCondition.await( skipNanos - diff, TimeUnit.NANOSECONDS );
                    lastTickTime = (float) skipNanos / 1000000000.0F;
                    this.tps = ( 1 / (double) lastTickTime );
                } else {
                    lastTickTime = (float) diff / 1000000000.0F;
                    this.tps = ( 1 / (double) lastTickTime );
                    LOGGER.warn( "Running behind: " + this.tps + " / " + ( 1 / ( skipNanos / 1000000000.0F ) ) + " tps" );
                }
            } catch ( InterruptedException e ) {
                // Ignored ._.
            } finally {
                tickLock.unlock();
            }
        }

        LOGGER.info( "Starting shutdown..." );

        // Safe shutdown
        this.networkManager.close();
        this.pluginManager.close();
        this.worldManager.close();

        int wait = 500;
        this.executorService.shutdown();
        while ( !this.executorService.isShutdown() && wait-- > 0 ) {
            try {
                this.executorService.awaitTermination( 10, TimeUnit.MILLISECONDS );
            } catch ( InterruptedException e ) {
                // Ignored .-.
            }
        }

        if ( !this.executorService.isShutdown() ) {
            List<Runnable> running = this.executorService.shutdownNow();
            for ( Runnable runnable : running ) {
                LOGGER.warn( "Runnable " + runnable.getClass().getName() + " has been terminated due to shutdown" );
            }
        }

        while ( !this.executorService.isTerminated() ) {
            try {
                Thread.sleep( 1 );
            } catch ( InterruptedException e ) {
                e.printStackTrace();
            }
        }

        // Tell jLine to close PLS
        if ( this.readerThread != null ) {
            try {
                this.readerThread.interrupt();
                this.readerThread.join();
            } catch ( InterruptedException e ) {
                e.printStackTrace();
            }
        }

        try {
            TerminalConsoleAppender.close();
        } catch ( IOException e ) {
            e.printStackTrace();
        }

        LOGGER.info( "Shutdown completed" );

        // Wait up to 5 seconds
        Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
        for ( Thread thread : threadSet ) {
            if ( thread.isDaemon() || thread.getId() == mainThread || ( thread.getThreadGroup().getParent() == null &&
                thread.getThreadGroup().getName().equals( "system" ) ) ) {
                continue;
            }

            LOGGER.warn( "Remaining thread after shutdown: " + thread.getName() + " (#" + thread.getId() + ")" );
            LOGGER.warn( "Status: " + thread.getState().name() + " - Threadgroup: " + thread.getThreadGroup().getName() );
            for ( StackTraceElement element : thread.getStackTrace() ) {
                LOGGER.warn( "  " + element.toString() );
            }
        }
    }

    private boolean initNetworking( String host, int port ) {
        try {
            this.networkManager.initialize( this.serverConfig.getMaxPlayers(), host, port );

            if ( this.serverConfig.isEnablePacketDumping() ) {
                File dumpDirectory = new File( this.serverConfig.getDumpDirectory() );
                if ( !dumpDirectory.exists() ) {
                    if ( !dumpDirectory.mkdirs() ) {
                        LOGGER.error( "Failed to create dump directory; please double-check your filesystem permissions" );
                        return false;
                    }
                } else if ( !dumpDirectory.isDirectory() ) {
                    LOGGER.error( "Dump directory path does not point to a valid directory" );
                    return false;
                }

                this.networkManager.setDumpingEnabled( true );
                this.networkManager.setDumpDirectory( dumpDirectory );
            }
        } catch ( SocketException e ) {
            LOGGER.error( "Failed to initialize networking", e );
            return false;
        }

        return true;
    }

    public WorldAdapter getDefaultWorld() {
        return this.worldManager.getWorld( this.serverConfig.getDefaultWorld() );
    }

    public RecipeManager getRecipeManager() {
        return this.recipeManager;
    }

    private void loadConfig() {
        this.serverConfig = new ServerConfig();

        try {
            this.serverConfig.initialize( new File( "server.cfg" ) );
        } catch ( IOException e ) {
            LOGGER.error( "server.cfg is corrupted: ", e );
            System.exit( -1 );
        }

        try ( FileWriter fileWriter = new FileWriter( new File( "server.cfg" ) ) ) {
            this.serverConfig.write( fileWriter );
        } catch ( IOException e ) {
            LOGGER.warn( "Could not save server.cfg: ", e );
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
                LOGGER.warn( "Failed to load world: " + name, e );
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

    @Override
    public <T extends Entity> T createEntity( Class<T> entityClass ) {
        return Entities.create( entityClass );
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
    public Collection<EntityPlayer> getPlayers() {
        List<EntityPlayer> playerList = new ArrayList<>();

        worldManager.getWorlds().forEach( worldAdapter -> playerList.addAll( worldAdapter.getPlayers() ) );

        return playerList;
    }

    @Override
    public GroupManager getGroupManager() {
        return this.permissionGroupManager;
    }

    @Override
    public EntityPlayer findPlayerByName( String target ) {
        for ( WorldAdapter adapter : worldManager.getWorlds() ) {
            for ( EntityPlayer player : adapter.getPlayers() ) {
                if ( player.getName().equals( target ) ) {
                    return player;
                }
            }
        }

        return null;
    }

    @Override
    public EntityPlayer findPlayerByUUID( UUID target ) {
        return this.playersByUUID.get( target );
    }

    @Override
    public int getPort() {
        return this.networkManager.getPort();
    }

    @Override
    public int getMaxPlayers() {
        return this.serverConfig.getMaxPlayers();
    }

    @Override
    public double getTPS() {
        return this.tps;
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

    @Override
    public boolean isMainThread() {
        return GoMintServer.mainThread == Thread.currentThread().getId();
    }

    public long getCurrentTickTime() {
        return this.currentTickTime;
    }

    // ------ GUI Stuff
    @Override
    public ButtonList createButtonList( String title ) {
        return new io.gomint.server.gui.ButtonList( title );
    }

    @Override
    public Modal createModal( String title, String question ) {
        return new io.gomint.server.gui.Modal( title, question );
    }

    @Override
    public CustomForm createCustomForm( String title ) {
        return new io.gomint.server.gui.CustomForm( title );
    }

    /**
     * Get the worlds config
     *
     * @param name of the world
     * @return the config for this world
     */
    public WorldConfig getWorldConfig( String name ) {
        for ( WorldConfig worldConfig : this.serverConfig.getWorlds() ) {
            if ( worldConfig.getName().equals( name ) ) {
                return worldConfig;
            }
        }

        return new WorldConfig();
    }

}
