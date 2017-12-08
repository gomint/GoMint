package io.gomint.server.network.handler;

import com.koloboke.collect.ObjCursor;
import io.gomint.entity.Entity;
import io.gomint.inventory.item.ItemStack;
import io.gomint.server.entity.EntityPlayer;
import io.gomint.server.network.PlayerConnection;
import io.gomint.server.network.packet.PacketMobEquipment;

import java.util.function.Predicate;

/**
 * @author geNAZt
 * @version 1.0
 */
public class PacketMobEquipmentHandler implements PacketHandler<PacketMobEquipment> {

    @Override
    public void handle( PacketMobEquipment packet, long currentTimeMillis, PlayerConnection connection ) {
        // Anti crash checks
        if ( packet.getSelectedSlot() > 8 ) {
            return;
        }

        // Ok the client wants to switch hotbar slot (itemInHand)
        ItemStack wanted = connection.getEntity().getInventory().getItem( packet.getSelectedSlot() );
        if ( wanted != null && wanted.equals( packet.getStack() ) && wanted.getAmount() == packet.getStack().getAmount() ) {
            // Inform the old item it got deselected
            io.gomint.server.inventory.item.ItemStack oldItemInHand =
                (io.gomint.server.inventory.item.ItemStack) connection.getEntity().getInventory().getItemInHand();
            oldItemInHand.removeFromHand( connection.getEntity() );

            // Set item in hand index
            connection.getEntity().getInventory().setItemInHand( packet.getSelectedSlot() );

            // Inform the item it got selected
            io.gomint.server.inventory.item.ItemStack newItemInHand =
                (io.gomint.server.inventory.item.ItemStack) connection.getEntity().getInventory().getItemInHand();
            newItemInHand.gotInHand( connection.getEntity() );

            // Relay packet
            ObjCursor<Entity> entityObjCursor = connection.getEntity().getAttachedEntities().cursor();
            while ( entityObjCursor.moveNext() ) {
                Entity entity = entityObjCursor.elem();
                if ( entity instanceof EntityPlayer ) {
                    ( (EntityPlayer) entity ).getConnection().addToSendQueue( packet );
                }
            }
        }
    }

}
