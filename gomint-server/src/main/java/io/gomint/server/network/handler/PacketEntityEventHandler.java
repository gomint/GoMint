/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.network.handler;

import io.gomint.entity.Entity;
import io.gomint.server.entity.EntityPlayer;
import io.gomint.server.network.PlayerConnection;
import io.gomint.server.network.packet.PacketEntityEvent;

/**
 * @author geNAZt
 * @version 1.0
 */
public class PacketEntityEventHandler implements PacketHandler<PacketEntityEvent> {

    @Override
    public void handle( PacketEntityEvent packet, long currentTimeMillis, PlayerConnection connection ) {
        switch ( packet.getEventId() ) {
            case 34:
                connection.getEntity().getEnchantmentProcessor().checkEntityEvent( (short) Math.abs( packet.getEventData() ) );
                break;

            default:
                for ( Entity entity : connection.getEntity().getAttachedEntities() ) {
                    if ( entity instanceof EntityPlayer ) {
                        ( (EntityPlayer) entity ).getConnection().addToSendQueue( packet );
                    }
                }

                connection.addToSendQueue( packet );
        }
    }

}
