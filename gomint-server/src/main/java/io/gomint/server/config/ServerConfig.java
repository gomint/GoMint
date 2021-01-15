/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.config;

import io.gomint.config.ConfigSection;
import io.gomint.config.annotation.Comment;
import io.gomint.config.YamlConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * Global configuration for the whole GoMint Server.
 *
 * @author BlackyPaw
 * @author geNAZt
 * @version 1.0
 */
public class ServerConfig extends YamlConfig {

    // ------------------------ General
    @Comment( "The host and port to bind the server to" )
    private ListenerConfig listener = new ListenerConfig();

    @Comment( "Config for connection options" )
    private ConnectionConfig connection = new ConnectionConfig();

    @Comment( "The maximum number of players to play on this server" )
    private int maxPlayers = 10;

    @Comment( "Motd of this server" )
    private String motd = "§aGoMint §7Development Build";

    // ------------------------ Packet Dumping
    @Comment( "Enables packet dumping for development purposes; not to be used for production" )
    private boolean enablePacketDumping = false;

    @Comment( "The directory to save packet dumps into if packet dumping is enabled" )
    private String dumpDirectory = "dumps";

    // ------------------------ World
    @Comment( "Name of the world to load on startup" )
    private String defaultWorld = "world";

    @Comment( "Load all worlds found at the start" )
    private boolean loadAllWorldsAtStart = false;

    @Comment( "Configure each world like you need to" )
    private List<WorldConfig> worlds = new ArrayList<WorldConfig>() {{
        add( new WorldConfig() );
    }};

    // ------------------------ Vanilla stuff
    @Comment( "Configure vanilla features. If you configure this to something else than default you are on your own. I will not support/solve issues when something has been changed here.")
    private VanillaConfig vanilla = new VanillaConfig();

    // ------------------------ Advanced Performance Settings
    @Comment( "Amount of Ticks per second which should be used to drive this server. 20 TPS is recommended for normal Servers. If you want PvP or Minigames you can set it higher but be sure to disable entities to have a stable TPS." )
    private int targetTPS = 20;

    @Comment( "Gomint has a automatic report system for bugs. This system collects data like player names and world names, cpu information and memory usage. You can disable collecting this data with this config")
    private boolean disableGomintReports = false;

    @Comment( "Control whether we want to load chunks when entities step over borders or not." )
    private boolean loadChunksForEntities = true;

    @Comment( "How many chunks should be sent per tick. The maximum without side effects is 4, reducing it may help with lower end clients." )
    private int sendChunksPerTick = 4;

    @Comment( "Enables fast join. This does disable sendChunksPerTick for the first batch if chunks needed to login. There is no " +
        "data between the chunks and thus delaying it is just letting the player wait longer on login")
    private boolean enableFastJoin = true;

    @Comment( "Enable client side chunk caching if the client supports it." )
    private boolean enableClientCache = true;

    public ListenerConfig listener() {
        return listener;
    }

    public ConnectionConfig connection() {
        return connection;
    }

    public int maxPlayers() {
        return maxPlayers;
    }

    public String motd() {
        return motd;
    }

    public boolean enablePacketDumping() {
        return enablePacketDumping;
    }

    public String dumpDirectory() {
        return dumpDirectory;
    }

    public String defaultWorld() {
        return defaultWorld;
    }

    public boolean loadAllWorldsAtStart() {
        return loadAllWorldsAtStart;
    }

    public List<WorldConfig> worlds() {
        return worlds;
    }

    public VanillaConfig vanilla() {
        return vanilla;
    }

    public int targetTPS() {
        return targetTPS;
    }

    public boolean disableGomintReports() {
        return disableGomintReports;
    }

    public boolean loadChunksForEntities() {
        return loadChunksForEntities;
    }

    public int sendChunksPerTick() {
        return sendChunksPerTick;
    }

    public boolean enableFastJoin() {
        return enableFastJoin;
    }

    public boolean enableClientCache() {
        return enableClientCache;
    }

    @Override
    public void update(ConfigSection section) {
        // Check if we have the new enabledClientCache config
        if (!section.has("enableClientCache")) {
            section.set("enableClientCache", true);
        }
    }

    @Override
    public String toString() {
        return "ServerConfig{" +
            "listener=" + listener +
            ", connection=" + connection +
            ", maxPlayers=" + maxPlayers +
            ", motd='" + motd + '\'' +
            ", enablePacketDumping=" + enablePacketDumping +
            ", dumpDirectory='" + dumpDirectory + '\'' +
            ", defaultWorld='" + defaultWorld + '\'' +
            ", loadAllWorldsAtStart=" + loadAllWorldsAtStart +
            ", worlds=" + worlds +
            ", vanilla=" + vanilla +
            ", targetTPS=" + targetTPS +
            ", disableGomintReports=" + disableGomintReports +
            ", loadChunksForEntities=" + loadChunksForEntities +
            ", sendChunksPerTick=" + sendChunksPerTick +
            ", enableFastJoin=" + enableFastJoin +
            '}';
    }
}
