/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.inventory;

import io.gomint.math.BlockPosition;
import io.gomint.server.entity.EntityPlayer;
import io.gomint.server.entity.tileentity.ChestTileEntity;
import io.gomint.server.network.PlayerConnection;
import io.gomint.server.network.packet.PacketInventoryContent;
import io.gomint.server.network.packet.PacketInventorySetSlot;
import io.gomint.server.network.type.WindowType;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ChestInventory extends ContainerInventory {

    public ChestInventory( InventoryHolder owner ) {
        super( owner, 27 );
    }

    @Override
    public void sendContents( PlayerConnection playerConnection ) {
        byte windowId = playerConnection.getEntity().getWindowId( this );

        PacketInventoryContent inventoryContent = new PacketInventoryContent();
        inventoryContent.setWindowId( windowId );
        inventoryContent.setItems( this.getContents() );
        playerConnection.send( inventoryContent );
    }

    @Override
    public void sendContents( int slot, PlayerConnection playerConnection ) {
        byte windowId = playerConnection.getEntity().getWindowId( this );

        PacketInventorySetSlot inventorySetSlot = new PacketInventorySetSlot();
        inventorySetSlot.setWindowId( windowId );
        inventorySetSlot.setSlot( slot );
        inventorySetSlot.setItemStack( this.getItem( slot ) );
        playerConnection.send( inventorySetSlot );
    }

    @Override
    public WindowType getType() {
        return WindowType.CONTAINER;
    }

    @Override
    public BlockPosition getContainerPosition() {
        ChestTileEntity tileEntity = (ChestTileEntity) this.owner;
        return tileEntity.getLocation().toBlockPosition();
    }

    @Override
    public void onOpen( EntityPlayer player ) {
        // Sound and open animation
    }

    @Override
    public void onClose( EntityPlayer player ) {
        // Sound and close animation
    }

}
