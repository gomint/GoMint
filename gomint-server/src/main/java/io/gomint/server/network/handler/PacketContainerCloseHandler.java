package io.gomint.server.network.handler;

import io.gomint.event.inventory.InventoryCloseEvent;
import io.gomint.inventory.item.ItemStack;
import io.gomint.math.Location;
import io.gomint.server.network.PlayerConnection;
import io.gomint.server.network.packet.PacketContainerClose;
import io.gomint.server.world.WorldAdapter;

/**
 * @author geNAZt
 * @version 1.0
 */
public class PacketContainerCloseHandler implements PacketHandler<PacketContainerClose> {

    @Override
    public void handle( PacketContainerClose packet, long currentTimeMillis, PlayerConnection connection ) {
        if ( packet.getWindowId() == -1 ) {
            InventoryCloseEvent inventoryCloseEvent = new InventoryCloseEvent( connection.getEntity(), connection.getEntity().getInventory() );
            connection.getServer().getPluginManager().callEvent( inventoryCloseEvent );

            WorldAdapter worldAdapter = connection.getEntity().getWorld();
            Location location = connection.getEntity().getLocation();

            // Push out all items in the crafting views
            for ( ItemStack stack : connection.getEntity().getCraftingInventory().getContentsArray() ) {
                worldAdapter.dropItem( location, stack );
            }

            // Client closed its crafting view
            connection.getEntity().getCraftingInventory().resizeAndClear( 4 );
            connection.getEntity().getCraftingResultInventory().resizeAndClear( 4 );
        } else {
            connection.getEntity().closeInventory( packet.getWindowId() );
        }
    }

}
