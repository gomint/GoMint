/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.network.handler;

import com.koloboke.collect.ObjCursor;
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
        ObjCursor<Entity> entityObjCursor = connection.getEntity().getAttachedEntities().cursor();
        while ( entityObjCursor.moveNext() ) {
            Entity entity = entityObjCursor.elem();
            if ( entity instanceof EntityPlayer ) {
                ( (EntityPlayer) entity ).getConnection().addToSendQueue( packet );
            }
        }

        connection.addToSendQueue( packet );
    }

}
