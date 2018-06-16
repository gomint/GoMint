package io.gomint.server.network.handler;

import io.gomint.server.network.PlayerConnection;
import io.gomint.server.network.packet.PacketInteract;

/**
 * @author geNAZt
 * @version 1.0
 */
public class PacketInteractHandler implements PacketHandler<PacketInteract> {

    @Override
    public void handle( PacketInteract packet, long currentTimeMillis, PlayerConnection connection ) {
        if ( packet.getAction() == PacketInteract.InteractAction.MOUSEOVER ) {
            io.gomint.entity.Entity entity = connection.getEntity().getWorld().findEntity( packet.getEntityId() );
            if ( entity != null ) {
                connection.getEntity().setHoverEntity( entity );
            }
        }
    }

}
