/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.network.handler;

import io.gomint.server.network.PlayerConnection;
import io.gomint.server.network.packet.PacketTileEntityData;
import io.gomint.server.world.block.Block;

/**
 * @author geNAZt
 * @version 1.0
 */
public class PacketTileEntityDataHandler implements PacketHandler<PacketTileEntityData> {

    @Override
    public void handle( PacketTileEntityData packet, long currentTimeMillis, PlayerConnection connection ) throws Exception {
        Block block = connection.getEntity().world().blockAt( packet.getPosition() );
        if ( block.tileEntity() != null ) {
            block.tileEntity().applyClientData( connection.getEntity(), packet.getCompound() );
        }
    }

}
