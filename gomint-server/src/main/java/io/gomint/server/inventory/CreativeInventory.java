package io.gomint.server.inventory;

import io.gomint.server.inventory.item.ItemStack;
import io.gomint.server.inventory.item.Items;
import io.gomint.server.network.PlayerConnection;
import io.gomint.server.network.packet.PacketInventoryContent;

import java.util.Collection;

/**
 * @author geNAZt
 * @version 1.0
 */
public class CreativeInventory extends Inventory {

    /**
     * Construct new creative inventory
     *
     * @param owner of this inventory, should be the server in this case
     */
    public CreativeInventory( InventoryHolder owner ) {
        super( owner, 0 );
    }

    @Override
    public void sendContents( PlayerConnection playerConnection ) {
        Collection<Integer> itemIds = Items.getAll();
        ItemStack[] itemStacks = new ItemStack[itemIds.size() - 1];

        int i = 0;
        for ( Integer id : itemIds ) {
            if ( id != 0 ) {
                itemStacks[i++] = new ItemStack( id, (short) 0, 1, null );
            }
        }

        PacketInventoryContent inventoryContent = new PacketInventoryContent();
        inventoryContent.setItems( itemStacks );
        inventoryContent.setWindowId( WindowMagicNumbers.CREATIVE.getId() );
        playerConnection.send( inventoryContent );
    }

    @Override
    public void sendContents( int slot, PlayerConnection playerConnection ) {
        // This is a virtual inventory, only sendContents is used
    }

}
