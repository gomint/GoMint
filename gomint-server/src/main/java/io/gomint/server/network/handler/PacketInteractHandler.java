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
        System.out.println( packet );

        if ( packet.getAction() == PacketInteract.InteractAction.MOUSEOVER ) {
            // Special case runtime entity 0
            if ( packet.getEntityId() == 0 ) {
                connection.getEntity().setHoverEntity( connection.getEntity() );
            } else {
                connection.getEntity().setHoverEntity( connection.getEntity().getWorld().findEntity( packet.getEntityId() ) );
            }
        }
    }

}
