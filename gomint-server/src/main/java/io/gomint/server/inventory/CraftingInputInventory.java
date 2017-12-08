package io.gomint.server.inventory;

import io.gomint.server.network.PlayerConnection;

/**
 * @author geNAZt
 * @version 1.0
 */
public class CraftingInputInventory extends Inventory {

    public CraftingInputInventory( InventoryHolder owner ) {
        super( owner, 4 );
    }

    @Override
    public void sendContents( PlayerConnection playerConnection ) {

    }

    @Override
    public void sendContents( int slot, PlayerConnection playerConnection ) {

    }

    public int getWidth() {
        return this.size == 4 ? 2 : 3;
    }

    public int getHeight() {
        return this.size == 4 ? 2 : 3;
    }


}
