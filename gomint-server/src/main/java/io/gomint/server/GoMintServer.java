/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server;

import com.google.common.util.concurrent.ListeningScheduledExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import joptsimple.OptionSet;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.UserInterruptException;
import org.jline.terminal.Terminal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.LockSupport;
import java.util.function.Supplier;
import java.util.jar.Manifest;

import io.gomint.GoMint;
import io.gomint.GoMintInstanceHolder;
import io.gomint.config.InvalidConfigurationException;
import io.gomint.enchant.Enchantment;
import io.gomint.entity.Entity;
import io.gomint.entity.EntityPlayer;
import io.gomint.gui.ButtonList;
import io.gomint.gui.CustomForm;
import io.gomint.gui.Modal;
import io.gomint.inventory.item.ItemStack;
import io.gomint.permission.GroupManager;
import io.gomint.player.PlayerSkin;
import io.gomint.plugin.StartupPriority;
import io.gomint.scoreboard.Scoreboard;
import io.gomint.server.assets.AssetsLibrary;
import io.gomint.server.config.ServerConfig;
import io.gomint.server.config.WorldConfig;
import io.gomint.server.crafting.RecipeManager;
import io.gomint.server.enchant.Enchantments;
import io.gomint.server.entity.Entities;
import io.gomint.server.entity.potion.Effects;
import io.gomint.server.entity.tileentity.TileEntities;
import io.gomint.server.inventory.CreativeInventory;
import io.gomint.server.inventory.InventoryHolder;
import io.gomint.server.inventory.item.Items;
import io.gomint.server.logging.TerminalConsoleAppender;
import io.gomint.server.network.EncryptionKeyFactory;
import io.gomint.server.network.NetworkManager;
import io.gomint.server.network.Protocol;
import io.gomint.server.network.upnp.UPNPClient;
import io.gomint.server.permission.PermissionGroupManager;
import io.gomint.server.plugin.SimplePluginManager;
import io.gomint.server.scheduler.CoreScheduler;
import io.gomint.server.scheduler.SyncTaskManager;
import io.gomint.server.util.ClassPath;
import io.gomint.server.util.Watchdog;
import io.gomint.server.util.Values;
import io.gomint.server.world.WorldAdapter;
import io.gomint.server.world.WorldLoadException;
import io.gomint.server.world.WorldManager;
import io.gomint.server.world.block.Blocks;
import io.gomint.server.world.generator.SimpleChunkGeneratorRegistry;
import io.gomint.taglib.AllocationLimitReachedException;
import io.gomint.world.World;
import io.gomint.world.WorldType;
import io.gomint.world.block.Block;
import io.gomint.world.generator.ChunkGenerator;
import io.gomint.world.generator.CreateOptions;
import io.gomint.world.generator.integrated.LayeredGenerator;
import io.gomint.world.generator.integrated.NormalGenerator;
import io.gomint.world.generator.integrated.VanillaGenerator;
import io.gomint.world.generator.integrated.VoidGenerator;

/**
 * @author BlackyPaw
 * @author Clockw1seLrd
 * @author geNAZt
 * @version 1.1
 */
public class GoMintServer implements GoMint, InventoryHolder {

    private static final Logger LOGGER = LoggerFactory.getLogger(GoMintServer.class);
    private static long mainThread;

    // Configuration
    private ServerConfig serverConfig;

    // Networking
    private NetworkManager networkManager;
    private EncryptionKeyFactory encryptionKeyFactory;

    // Player lookups
    private Map<UUID, EntityPlayer> playersByUUID = new ConcurrentHashMap<>();

    // World Management
    private WorldManager worldManager;
    private String defaultWorld;
    private SimpleChunkGeneratorRegistry chunkGeneratorRegistry;

    // Game Information
    private RecipeManager recipeManager;
    private CreativeInventory creativeInventory;
    private PermissionGroupManager permissionGroupManager;

    // Plugin Management
    private SimplePluginManager pluginManager;

    // Task Scheduling
    private SyncTaskManager syncTaskManager;
    private final AtomicBoolean running = new AtomicBoolean(true);
    private final AtomicBoolean init = new AtomicBoolean(true);
    private ListeningScheduledExecutorService executorService;
    private Thread readerThread;
    private long currentTickTime;
    private long internalDiffTime;
    private long sleepBalance;
    private CoreScheduler scheduler;

    // Additional informations for API usage
    private double tps;

    // Watchdog
    private Watchdog watchdog;

    // Core utils
    private Blocks blocks;
    private Items items;
    private Enchantments enchantments;
    private Entities entities;
    private Effects effects;
    private TileEntities tileEntities;

    private final UUID serverUniqueID = UUID.randomUUID();
    private String gitHash;

    private BlockingQueue<Runnable> mainThreadWork = new LinkedBlockingQueue<>();

    private long start = System.currentTimeMillis();

    /**
     * Starts the GoMint server
     */
    public GoMintServer() throws IOException {
        // ------------------------------------ //
        // Executor Initialization
        // ------------------------------------ //
        this.executorService = MoreExecutors.listeningDecorator(Executors.newScheduledThreadPool(4, new ThreadFactory() {
            private final AtomicLong counter = new AtomicLong(0);

            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                thread.setName("GoMint Thread #" + counter.incrementAndGet());
                return thread;
            }
        }));

        this.watchdog = new Watchdog(this);
        this.watchdog.add(30, TimeUnit.SECONDS);

        GoMintServer.mainThread = Thread.currentThread().getId();
        GoMintInstanceHolder.setInstance(this);

        this.chunkGeneratorRegistry = new SimpleChunkGeneratorRegistry();
        this.chunkGeneratorRegistry().registerGenerator(LayeredGenerator.NAME, LayeredGenerator.class);
        this.chunkGeneratorRegistry().registerGenerator(NormalGenerator.NAME, NormalGenerator.class);
        this.chunkGeneratorRegistry().registerGenerator(VoidGenerator.NAME, VoidGenerator.class);
        this.chunkGeneratorRegistry().registerGenerator(VanillaGenerator.NAME, VanillaGenerator.class);

        // Extract information from the manifest
        String buildVersion = "dev/unsupported";
        Module module = getClass().getModule();
        try (InputStream is = module.getResourceAsStream("META-INF/MANIFEST.MF")) {
            if (is != null) {
                Manifest manifest = new Manifest(is);
                buildVersion = manifest.getMainAttributes().getValue("Implementation-Build");

                if (buildVersion == null) {
                    buildVersion = "dev/unsupported";
                }
            }
        } catch (IOException e) {
            // Ignored .-.
        }

        this.gitHash = buildVersion;

        LOGGER.info("Starting {}", version());
        Thread.currentThread().setName("GoMint Main Thread");

        LOGGER.info("Loading block, item and entity registers");

        ClassPath classPath = new ClassPath("io.gomint.server");

        this.items = new Items(classPath);
        this.blocks = new Blocks();
        this.items.setBlocks(this.blocks);

        LOGGER.info("Initializing recipes...");
        this.recipeManager = new RecipeManager();

        // Load assets from file:
        LOGGER.info("Loading assets library...");
        AssetsLibrary assets = new AssetsLibrary(this.items);

        try {
            assets.load(this.recipeManager);
        } catch (IOException | AllocationLimitReachedException e) {
            LOGGER.error("Failed to load assets library", e);
            return;
        }

        // ------------------------------------ //
        // Build up registries
        // ------------------------------------ //
        this.tileEntities = new TileEntities(classPath, this.items);
        this.blocks.init(classPath, this.items, this.tileEntities, assets.getBlockPalette());
        this.entities = new Entities(classPath);
        this.effects = new Effects(classPath);
        this.enchantments = new Enchantments(classPath);
        this.creativeInventory = assets.creativeInventory();

        // ------------------------------------ //
        // Configuration Initialization
        // ------------------------------------ //
        this.loadConfig();

        // ------------------------------------ //
        // Scheduler + WorldManager
        // ------------------------------------ //
        this.syncTaskManager = new SyncTaskManager();
        this.scheduler = new CoreScheduler(this.executorService(), this.syncTaskManager());
        this.worldManager = new WorldManager(this);

        // PluginManager Initialization
        this.pluginManager = new SimplePluginManager(this);
        this.pluginManager.detectPlugins();
        this.pluginManager.loadPlugins(StartupPriority.STARTUP);
    }

    public void startAfterRegistryInit(OptionSet args) {
        // ------------------------------------ //
        // jLine setup
        // ------------------------------------ //
        BlockingQueue<String> inputLines = new LinkedBlockingQueue<>();
        LineReader reader = null;
        Terminal terminal = TerminalConsoleAppender.getTerminal();
        if (terminal != null) {
            reader = LineReaderBuilder.builder()
                .appName("GoMint")
                .terminal(terminal)
                .completer((lineReader, parsedLine, list) -> {
                    List<String> suggestions = pluginManager.getCommandManager().completeSystem(parsedLine.line());
                    for (String suggestion : suggestions) {
                        LOGGER.info(suggestion);
                    }
                })
                .build();

            reader.setKeyMap("emacs");

            TerminalConsoleAppender.setReader(reader);
        }

        // ------------------------------------ //
        // Setup jLine reader thread
        // ------------------------------------ //
        Supplier<String> readerFunc;
        if (reader != null) {
            LineReader finalReader = reader;
            readerFunc = () -> {
                return finalReader.readLine("\u001b[32;0mGoMint\u001b[39;0m> ");
            };
        } else {
            BufferedReader stdinReader = new BufferedReader(new InputStreamReader(System.in));
            readerFunc = () -> {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException ignored) {
                    // Ignore
                }

                if (System.console() != null) {
                    return System.console().readLine();
                }

                try {
                    return stdinReader.readLine();
                } catch (IOException e) {
                    LOGGER.error("Could not read stdin", e);
                }

                return "";
            };
        }

        AtomicBoolean reading = new AtomicBoolean(false);

        this.readerThread = new Thread(() -> {
            String line;
            while (running.get()) {
                // Read jLine
                reading.set(true);
                try {
                    line = readerFunc.get();
                    if (line != null) {
                        inputLines.offer(line);
                    }
                } catch (UserInterruptException e) {
                    GoMintServer.this.shutdown();
                } catch (Exception e) {
                    LOGGER.error("jLine failed with following exception", e);
                }
            }
        });

        this.readerThread.setName("GoMint CLI reader");
        this.readerThread.start();

        while (!reading.get()) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                // Ignored .-.
            }
        }

        this.defaultWorld = this.serverConfig.defaultWorld();
        this.currentTickTime = System.currentTimeMillis();

        if (!this.isRunning()) {
            this.internalShutdown();
            return;
        }

        // ------------------------------------ //
        // Pre World Initialization
        // ------------------------------------ //

        this.permissionGroupManager = new PermissionGroupManager();

        // ------------------------------------ //
        // Networking Initialization
        // ------------------------------------ //
        int    port = args.has("lp") ?    (int) args.valueOf("lp") : this.serverConfig.listener().port();
        String host = args.has("lh") ? (String) args.valueOf("lh") : this.serverConfig.listener().ip();

        this.encryptionKeyFactory = new EncryptionKeyFactory(this.serverConfig.connection().jwtRoot());
        this.networkManager = new NetworkManager(this);

        if (!this.initNetworking(host, port)) {
            this.internalShutdown();
            return;
        }

        // ------------------------------------ //
        // World Initialization
        // ------------------------------------ //
        // CHECKSTYLE:OFF
        try {
            this.worldManager.loadWorld(this.serverConfig.defaultWorld());
        } catch (WorldLoadException e) {
            // Get world config of default world
            WorldConfig worldConfig = this.worldConfigOf(this.defaultWorld);

            // Get chunk generator which might have been changed in the world config
            Class<? extends ChunkGenerator> chunkGenerator;
            chunkGenerator = this.chunkGeneratorRegistry().generatorClass(worldConfig.chunkGenerator());

            // Create options world generator
            CreateOptions options = new CreateOptions();
            options.worldType(WorldType.PERSISTENT); // Persistent world storage

            // Check if wished chunk generator is present
            if (chunkGenerator != null) {
                options.generator(chunkGenerator);
            } else {
                // Apply standard generator
                options.generator(NormalGenerator.class);

                // Log chunk generator failure
                LOGGER.warn("No such chunk generator for '{}' - Using {}",
                    worldConfig.chunkGenerator(), NormalGenerator.class.getName());
            }

            // Try to generate world
            World world = this.worldManager.createWorld(this.defaultWorld, options);
            if (world == null) {
                LOGGER.error("Failed to load or generate default world", e);
                this.internalShutdown();
                return;
            }
        }
        // CHECKSTYLE:ON

        if (this.serverConfig.listener().useUPNP()) {
            var client = new UPNPClient();
            client.portForward(port);
        }

        motd(this.serverConfig().motd());

        // ------------------------------------ //
        // Load plugins with StartupPriority LOAD
        // ------------------------------------ //
        this.pluginManager.loadPlugins(StartupPriority.LOAD);
        if (!this.isRunning()) {
            this.internalShutdown();
            return;
        }

        this.pluginManager.installPlugins();

        if (!this.isRunning()) {
            this.internalShutdown();
            return;
        }

        try {
            if (GoMintServerHelper.minecraftLoopbackExemptIsNotPermitted()) {
                GoMintServerHelper.offerMinecraftLoopbackExempt();
            }
        } catch (IOException | InterruptedException ex) {
            System.err.println("Failed determining loopback exempt status of Minecraft:");
            ex.printStackTrace();
        }

        init.set(false);
        LOGGER.info("Done in {} ms", (System.currentTimeMillis() - start));
        this.watchdog.done();

        if (args.has("exit-after-boot")) {
            this.internalShutdown();
            return;
        }

        // ------------------------------------ //
        // Main Loop
        // ------------------------------------ //

        // Calculate the nanoseconds we need for the tick loop
        int targetTPS = this.serverConfig().targetTPS();
        if (targetTPS > 1000) {
            LOGGER.warn("Setting target TPS above 1k is not supported, target TPS has been set to 1k");
            targetTPS = 1000;
        }

        long skipNanos = TimeUnit.SECONDS.toNanos(1) / targetTPS;
        LOGGER.info("Setting skipNanos to: {}", skipNanos);

        // Tick loop
        float lastTickTime = Float.MIN_NORMAL;

        while (this.running.get()) {
            // Tick all major subsystems:
            this.currentTickTime = System.currentTimeMillis();
            this.internalDiffTime = System.nanoTime();
            this.watchdog.add(this.currentTickTime, 30, TimeUnit.SECONDS);

            // Drain input lines
            while (!inputLines.isEmpty()) {
                String line = inputLines.poll();
                if (line != null) {
                    this.pluginManager.getCommandManager().executeSystem(line);
                }
            }

            // Tick remaining work
            while (!this.mainThreadWork.isEmpty()) {
                Runnable runnable = this.mainThreadWork.poll();
                if (runnable != null) {
                    runnable.run();
                }
            }

            // Tick networking at every tick
            this.networkManager.update(this.currentTickTime, lastTickTime);

            this.syncTaskManager.update(this.currentTickTime);
            this.worldManager.update(this.currentTickTime, lastTickTime);
            this.permissionGroupManager.update(this.currentTickTime, lastTickTime);

            this.watchdog.done();

            // Check if we got shutdown
            if (!this.running.get()) {
                break;
            }

            long startSleep = System.nanoTime();
            long diff = startSleep - this.internalDiffTime;
            boolean warn = false;
            if (diff <= skipNanos) {
                long sleepNeeded = (skipNanos - diff) - this.sleepBalance;
                this.sleepBalance = 0;

                LockSupport.parkNanos(sleepNeeded);

                long endSleep = System.nanoTime();
                long sleptFor = endSleep - startSleep;
                diff = skipNanos;

                if ( sleptFor > sleepNeeded ) {
                    this.sleepBalance = sleptFor - sleepNeeded;
                }
            } else {
                warn = true;
            }

            lastTickTime = (float) diff / TimeUnit.SECONDS.toNanos(1);
            this.tps = (1 / (double) lastTickTime);

            // Due to the fact that we
            if ( this.tps > this.serverConfig.targetTPS() ) {
                this.tps = this.serverConfig.targetTPS();
            }

            if (warn) {
                LOGGER.warn("Running behind: {} / {} tps", this.tps, (1 / (skipNanos / (float) TimeUnit.SECONDS.toNanos(1))));
            }
        }

        this.internalShutdown();
    }

    private void internalShutdown() {
        LOGGER.info("Starting shutdown...");

        // Safe shutdown
        this.pluginManager.close();

        LOGGER.info("Uninstalled all plugins");

        if (this.networkManager != null) {
            this.networkManager.shutdown();
        }

        if (this.worldManager != null) {
            this.worldManager.close();
        }

        LOGGER.info("Starting shutdown of the main executor");

        int wait = (int) Values.CLIENT_TICK_MS;
        this.executorService.shutdown();
        while (!this.executorService.isTerminated() && wait-- > 0) {
            try {
                this.executorService.awaitTermination(100, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                // Ignored .-.
            }
        }

        LOGGER.info("Shutdown of main executor completed");

        if (wait <= 0) {
            List<Runnable> remainRunning = this.executorService.shutdownNow();
            for (Runnable runnable : remainRunning) {
                LOGGER.warn("Runnable " + runnable.getClass().getName() + " has been terminated due to shutdown");
            }
        }

        LOGGER.info("Shutting down terminal");

        // Tell jLine to close PLS
        if (this.readerThread != null) {
            this.readerThread.interrupt();
        }

        LOGGER.info("jLine thread has been shutdown");

        try {
            TerminalConsoleAppender.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        LOGGER.info("Shutdown completed");

        // Wait up to 10 seconds
        if (this.announceThreads()) {
            long start = System.currentTimeMillis();
            while ((System.currentTimeMillis() - start) < TimeUnit.SECONDS.toMillis(10)) {
                try {
                    Thread.sleep(1000);
                    if (!this.announceThreads()) {
                        break;
                    }
                } catch (InterruptedException e) {
                    // Ignore
                }
            }
        }

        System.exit(0);
    }

    private boolean announceThreads() {
        boolean foundThread = false;

        Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
        for (Thread thread : threadSet) {
            if (thread.isDaemon() || thread.getId() == mainThread || (thread.getThreadGroup() != null &&
                thread.getThreadGroup().getName().equals("gomint-internal")) || (thread.getThreadGroup() != null &&
                thread.getThreadGroup().getParent() == null && thread.getThreadGroup().getName().equals("system"))) {
                continue;
            }

            foundThread = true;

            LOGGER.warn("Remaining thread after shutdown: {} (#{})", thread.getName(), thread.getId());
            LOGGER.warn("Status: {} - Threadgroup: {}", thread.getState(), (thread.getThreadGroup() != null) ? thread.getThreadGroup().getName() : "NULL");
            for (StackTraceElement element : thread.getStackTrace()) {
                LOGGER.warn("  {}", element);
            }
        }

        return foundThread;
    }

    private boolean initNetworking(String host, int port) {
        try {
            this.networkManager.initialize(this.serverConfig.maxPlayers(), host, port);

            if (this.serverConfig.enablePacketDumping()) {
                File dumpDirectory = new File(this.serverConfig.dumpDirectory());
                if (!dumpDirectory.exists()) {
                    if (!dumpDirectory.mkdirs()) {
                        LOGGER.error("Failed to create dump directory; please double-check your filesystem permissions");
                        return false;
                    }
                } else if (!dumpDirectory.isDirectory()) {
                    LOGGER.error("Dump directory path does not point to a valid directory");
                    return false;
                }

                this.networkManager.setDumpingEnabled(true);
                this.networkManager.setDumpDirectory(dumpDirectory);
            }
        } catch (Exception e) {
            LOGGER.error("Failed to initialize networking", e);
            return false;
        }

        return true;
    }

    public GoMint changeDefaultWorld(World world) {
        if (world == null) {
            LOGGER.warn("Can't set default world to null");
            return this;
        }

        this.defaultWorld = world.folder();
        return this;
    }

    public SimpleChunkGeneratorRegistry chunkGeneratorRegistry() {
        return chunkGeneratorRegistry;
    }

    public GoMint motd(String motd) {
        this.networkManager.setMotd(motd);
        return this;
    }

    public int currentPlayerCount() {
        return this.playersByUUID.size();
    }

    @Override
    public <T extends Block> T createBlock(Class<T> blockClass) {
        return (T) this.blocks.get(blockClass);
    }
    
    @Override
    public ButtonList createButtonList(String title) {
        return new io.gomint.server.gui.ButtonList(title);
    }

    @Override
    public CustomForm createCustomForm(String title) {
        return new io.gomint.server.gui.CustomForm(title);
    }

    @Override
    public <T extends Enchantment> T createEnchantment(Class<T> enchantmentClass, int level) {
        return (T) this.enchantments.create(enchantmentClass, (short) (level - 1));
    }

    @Override
    public <T extends Entity<T>> T createEntity(Class<T> entityClass) {
        return this.entities.create(entityClass);
    }

    @Override
    public <T extends ItemStack<T>> T createItemStack(Class<T> itemClass, int amount) {
        return this.items.create(itemClass, (byte) amount);
    }

    @Override
    public Modal createModal(String title, String question) {
        return new io.gomint.server.gui.Modal(title, question);
    }

    @Override
    public PlayerSkin createPlayerSkin(InputStream inputStream) {
        try {
            return io.gomint.server.player.PlayerSkin.fromInputStream(inputStream);
        } catch (IOException e) {
            LOGGER.error("Could not read skin from input: ", e);
            return null;
        }
    }

    @Override
    public Scoreboard createScoreboard() {
        return new io.gomint.server.scoreboard.Scoreboard();
    }

    @Override
    public World createWorld(String name, CreateOptions options) {
        return this.worldManager.createWorld(name, options);
    }

    public WorldAdapter defaultWorld() {
        return this.worldManager.getWorld(this.defaultWorld);
    }

    @Override
    public GoMint dispatchCommand(String line) {
        this.pluginManager.getCommandManager().executeSystem(line);
        return this;
    }

    public PlayerSkin emptyPlayerSkin() {
        return io.gomint.server.player.PlayerSkin.emptySkin();
    }

    @Override
    public EntityPlayer findPlayerByName(String target) {
        for (WorldAdapter adapter : worldManager.getWorlds()) {
            for (EntityPlayer player : adapter.onlinePlayers()) {
                if (player.name().equalsIgnoreCase(target)) {
                    return player;
                }
            }
        }

        return null;
    }

    @Override
    public EntityPlayer findPlayerByUUID(UUID target) {
        return this.playersByUUID.get(target);
    }

    public GroupManager groupManager() {
        return this.permissionGroupManager;
    }

    @Override
    public boolean mainThread() {
        return GoMintServer.mainThread == Thread.currentThread().getId();
    }

    public int maxPlayerCount() {
        return this.serverConfig.maxPlayers();
    }

    public String motd() {
        return this.networkManager.getMotd();
    }

    public Collection<EntityPlayer> onlinePlayers() {
        var playerList = new ArrayList<EntityPlayer>();
        worldManager.getWorlds().forEach(world -> playerList.addAll(world.onlinePlayers()));
        return playerList;
    }

    public SimplePluginManager pluginManager() {
        return pluginManager;
    }

    public int port() {
        return this.networkManager.getPort();
    }

    @Override
    public GoMint shutdown() {
        this.running.set(false);
        return this;
    }

    public double tps() {
        return this.tps;
    }

    public String version() {
        return "GoMint 1.0.0 (MC:BE "
            + Protocol.MINECRAFT_PE_NETWORK_VERSION
            + " ["
            + Protocol.MINECRAFT_PE_PROTOCOL_VERSION
            + "]) - "
            + this.gitHash;
    }

    public World world(String name) {
        World world = this.worldManager.getWorld(name);
        if (world == null) {
            // Try to load the world

            // CHECKSTYLE:OFF
            try {
                return this.worldManager.loadWorld(name);
            } catch (WorldLoadException e) {
                LOGGER.warn("Failed to load world: " + name, e);
                return null;
            }
            // CHECKSTYLE:ON
        }

        return world;
    }

    public Collection<World> worlds() {
        return Collections.unmodifiableCollection(this.worldManager.getWorlds());
    }

    public CreativeInventory creativeInventory() {
        return this.creativeInventory;
    }

    public long currentTickTime() {
        return this.currentTickTime;
    }

    public WorldConfig worldConfigOf(String name) {
        for (WorldConfig worldConfig : this.serverConfig.worlds()) {
            if (worldConfig.name().equals(name)) {
                return worldConfig;
            }
        }

        return new WorldConfig();
    }

    public RecipeManager recipeManager() {
        return this.recipeManager;
    }

    public boolean isRunning() {
        return this.running.get();
    }

    public void addToMainThread(Runnable runnable) {
        this.mainThreadWork.offer(runnable);
    }

    public ServerConfig serverConfig() {
        return serverConfig;
    }

    public NetworkManager networkManager() {
        return networkManager;
    }

    public EncryptionKeyFactory encryptionKeyFactory() {
        return encryptionKeyFactory;
    }

    public Map<UUID, EntityPlayer> uuidMappedPlayers() {
        return playersByUUID;
    }

    public SyncTaskManager syncTaskManager() {
        return syncTaskManager;
    }

    public ListeningScheduledExecutorService executorService() {
        return executorService;
    }

    public CoreScheduler scheduler() {
        return scheduler;
    }

    public WorldManager worldManager() {
        return worldManager;
    }

    public Watchdog watchdog() {
        return watchdog;
    }

    public Blocks blocks() {
        return blocks;
    }

    public Items items() {
        return items;
    }

    public Enchantments enchantments() {
        return enchantments;
    }

    public Entities entities() {
        return entities;
    }

    public Effects effects() {
        return effects;
    }

    public TileEntities tileEntities() {
        return tileEntities;
    }

    public UUID serverUniqueID() {
        return serverUniqueID;
    }

    public String gitHash() {
        return gitHash;
    }

    private void loadConfig() {
        this.serverConfig = new ServerConfig();

        try {
            this.serverConfig.init(new File("server.yml"));
        } catch (InvalidConfigurationException e) {
            LOGGER.error("server.cfg is corrupted: ", e);
            System.exit(-1);
        }

        LOGGER.info("Loaded config...");
    }

}
