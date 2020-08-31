package io.gomint.server.inventory;

import io.gomint.inventory.InventoryType;
import io.gomint.server.network.PlayerConnection;
import io.gomint.server.network.packet.PacketCreativeContent;

/**
 * @author geNAZt
 * @version 1.0
 */
public class CreativeInventory extends Inventory {

    private final PacketCreativeContent inventoryContent = new PacketCreativeContent();;

    /**
     * Construct new creative inventory
     *
     * @param owner of this inventory, should be the server in this case
     * @param size  of the inventory
     */
    public CreativeInventory( InventoryHolder owner, int size ) {
        super( null, owner, size );

        this.inventoryContent.setItems( getContentsArray() );
    }

    @Override
    public void sendContents( PlayerConnection playerConnection ) {
        playerConnection.addToSendQueue( this.inventoryContent );
    }

    @Override
    public void sendContents( int slot, PlayerConnection playerConnection ) {
        // This is a virtual inventory, only sendContents is used
    }

    @Override
    public InventoryType getInventoryType() {
        return InventoryType.CREATIVE;
    }

}
