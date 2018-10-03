/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.config;

import io.gomint.config.Comment;
import io.gomint.config.YamlConfig;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

/**
 * Global configuration for the whole GoMint Server.
 *
 * @author BlackyPaw
 * @author geNAZt
 * @version 1.0
 */
@NoArgsConstructor
@Getter
@ToString
public class ServerConfig extends YamlConfig {

    // ------------------------ General
    @Comment( "The host and port to bind the server to" )
    private ListenerConfig listener = new ListenerConfig();

    @Comment( "Config for connection options" )
    private ConnectionConfig connection = new ConnectionConfig();

    @Comment( "The maximum number of players to play on this server" )
    private int maxPlayers = 10;

    @Comment( "If you want to allow non XBOX logged in users to join this server set this to false" )
    private boolean onlyXBOXLogin = true;

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

    @Comment( "Configure a default world setting" )
    private WorldConfig defaultWorldSettings = new WorldConfig();

    @Comment( "Configure each world like you need to" )
    private Map<String, WorldConfig> worlds = new HashMap<String, WorldConfig>(){{
        put("world", new WorldConfig());
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

    @Comment( "How much chunks do you want to send to the client per client tick (50ms). Vanilla defaults to 4, but i " +
        "tested its more or less safe to up to 1024 per tick for Windows 10 Client and Android on a LG G2. Recommended is not to set " +
        "this value to anything higher than 32, else other packets like movement or chat may be delayed to long")
    private int sendChunksPerTick = 16;

    @Comment( "Enables fast join. This does disable sendChunksPerTick for the first batch if chunks needed to login. There is no " +
        "data between the chunks and thus delaying it is just letting the player wait longer on login")
    private boolean enableFastJoin = true;

}
