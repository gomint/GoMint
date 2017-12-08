/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.inventory;

import io.gomint.server.network.PlayerConnection;
import io.gomint.server.network.packet.PacketInventoryContent;
import io.gomint.server.network.packet.PacketInventorySetSlot;

/**
 * @author geNAZt
 * @version 1.0
 */
public class OffhandInventory extends Inventory {

    public OffhandInventory( InventoryHolder owner ) {
        super( owner, 1 );
    }

    @Override
    public void sendContents( PlayerConnection playerConnection ) {
        PacketInventoryContent inventory = new PacketInventoryContent();
        inventory.setWindowId( WindowMagicNumbers.OFFHAND.getId() );
        inventory.setItems( getContents() );
        playerConnection.addToSendQueue( inventory );
    }

    @Override
    public void sendContents( int slot, PlayerConnection playerConnection ) {
        PacketInventorySetSlot setSlot = new PacketInventorySetSlot();
        setSlot.setSlot( slot );
        setSlot.setWindowId( WindowMagicNumbers.OFFHAND.getId() );
        setSlot.setItemStack( this.contents[slot] );
        playerConnection.addToSendQueue( setSlot );
    }

}
