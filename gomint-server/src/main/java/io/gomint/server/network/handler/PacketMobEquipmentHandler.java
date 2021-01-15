package io.gomint.server.network.handler;

import io.gomint.inventory.item.ItemStack;
import io.gomint.server.network.PlayerConnection;
import io.gomint.server.network.packet.PacketMobEquipment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author geNAZt
 * @version 1.0
 */
public class PacketMobEquipmentHandler implements PacketHandler<PacketMobEquipment> {

    private static final Logger LOGGER = LoggerFactory.getLogger(PacketMobEquipmentHandler.class);

    @Override
    public void handle( PacketMobEquipment packet, long currentTimeMillis, PlayerConnection connection ) {
        // Anti crash checks
        if ( packet.getSelectedSlot() > 8 ) {
            return;
        }

        // Ok the client wants to switch hotbar slot (itemInHand)
        ItemStack<?> wanted = connection.getEntity().getInventory().item( packet.getSelectedSlot() );
        if ( wanted != null && wanted.equals( packet.getStack() ) && wanted.amount() == packet.getStack().amount() ) {
            connection.getEntity().getInventory().setItemInHand( packet.getSelectedSlot() );
            connection.getEntity().setUsingItem( false );
        } else {
            // Reset client
            LOGGER.debug("Item mismatch: {} / {}", packet.getStack(), wanted);
            connection.getEntity().getInventory().sendItemInHand();
        }
    }

}
