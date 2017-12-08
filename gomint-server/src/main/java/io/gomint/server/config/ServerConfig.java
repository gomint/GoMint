/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.config;

import com.blackypaw.simpleconfig.SimpleConfig;
import com.blackypaw.simpleconfig.annotation.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Global configuration for the whole GoMint Server.
 *
 * @author BlackyPaw
 * @author geNAZt
 * @version 1.0
 */
@NoArgsConstructor
@Getter
public class ServerConfig extends SimpleConfig {

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

    @Comment( "Configure each world like you need to" )
    private List<WorldConfig> worlds = new ArrayList<WorldConfig>(){{
        add( new WorldConfig() );
    }};

    // ------------------------ Advanced Performance Settings
    @Comment( "Amount of Ticks per second which should be used to drive this server. 20 TPS is recommended for normal Servers. If you want PvP or Minigames you can set it higher but be sure to disable entities to have a stable TPS." )
    private int targetTPS = 20;

    @Comment( "Control whether we want to load chunks when entities step over borders or not." )
    private boolean loadChunksForEntities = true;

}
