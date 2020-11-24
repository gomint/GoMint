/*
 *  Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
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
    }

}
