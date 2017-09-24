package io.gomint.server.network.handler;

import io.gomint.server.entity.EntityPlayer;
import io.gomint.server.network.PlayerConnection;
import io.gomint.server.network.packet.PacketContainerClose;

/**
 * @author geNAZt
 * @version 1.0
 */
public class PacketContainerCloseHandler implements PacketHandler<PacketContainerClose> {

    @Override
    public void handle( PacketContainerClose packet, long currentTimeMillis, PlayerConnection connection ) {
        if ( packet.getWindowId() == -1 ) {
            // Client closed its crafting view
            connection.getEntity().getCraftingInventory().resizeAndClear( 4 );
            connection.getEntity().getCraftingResultInventory().resizeAndClear( 4 );
        } else {
            connection.getEntity().closeInventory( packet.getWindowId() );
        }
    }

}
