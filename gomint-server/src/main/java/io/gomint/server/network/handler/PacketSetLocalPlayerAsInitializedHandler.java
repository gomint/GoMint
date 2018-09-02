/*
 *  Copyright (c) 2018, GoMint, BlackyPaw and geNAZt
 *
 *  This code is licensed under the BSD license found in the
 *  LICENSE file in the root directory of this source tree.
 */
package io.gomint.server.network.handler;

import io.gomint.server.network.PlayerConnection;
import io.gomint.server.network.packet.PacketSetLocalPlayerAsInitialized;

public class PacketSetLocalPlayerAsInitializedHandler implements PacketHandler<PacketSetLocalPlayerAsInitialized> {

    @Override
    public void handle( PacketSetLocalPlayerAsInitialized packet, long currentTimeMillis, PlayerConnection connection ) {
        // Client seems to be ready to spawn players
        connection.spawnPlayerEntities();

        // Test for scoreboard
        /*if ( connection.getProtocolID() == Protocol.MINECRAFT_PE_BETA_PROTOCOL_VERSION ) {
            connection.getServer().getSyncTaskManager().addTask( new SyncScheduledTask( connection.getServer().getSyncTaskManager(), () -> {
                Scoreboard scoreboard = new Scoreboard();
                ScoreboardDisplay display = scoreboard.addDisplaySlot( DisplaySlot.SIDEBAR, "obj1", ChatColor.GREEN + "Go" + ChatColor.DARK_GREEN + "Mint " + ChatColor.GOLD + "DevBuild" );
                display.addString( " ", 0 );
                display.addString( "Build Number", 1 );
                display.addString( "dev i guess", 2 );
                display.addString( "  ", 3 );
                display.addString( "end of the display", 4 );
                scoreboard.showFor( connection.getEntity() );
            }, 1, -1, TimeUnit.SECONDS ) );
        }*/
    }

}
