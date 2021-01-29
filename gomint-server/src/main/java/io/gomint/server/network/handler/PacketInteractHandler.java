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
            io.gomint.entity.Entity<?> entity = connection.entity().world().findEntity( packet.getEntityId() );
            if ( entity != null ) {
                connection.entity().hoverEntity( entity );
            }
        }

        // Player requested to open their own inventory?
        if ( packet.getAction() == PacketInteract.InteractAction.OPEN_INVENTORY ) {
            connection.entity().openInventory(connection.entity().inventory());
        }
    }

}
