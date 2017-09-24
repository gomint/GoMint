package io.gomint.server.inventory;

import io.gomint.inventory.item.ItemStack;
import io.gomint.server.network.PlayerConnection;
import io.gomint.server.network.packet.PacketInventoryContent;
import io.gomint.server.network.packet.PacketInventorySetSlot;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ArmorInventory extends Inventory {

    public ArmorInventory( InventoryHolder owner ) {
        super( owner, 4 );
    }

    public void setHelmet( ItemStack itemStack ) {
        this.setItem( 0, itemStack );
    }

    public void setChestplate( ItemStack itemStack ) {
        this.setItem( 1, itemStack );
    }

    public void setLeggings( ItemStack itemStack ) {
        this.setItem( 2, itemStack );
    }

    public void setBoots( ItemStack itemStack ) {
        this.setItem( 3, itemStack );
    }

    @Override
    public void sendContents( PlayerConnection playerConnection ) {
        if ( playerConnection.getEntity().equals( this.owner ) ) {
            PacketInventoryContent inventory = new PacketInventoryContent();
            inventory.setWindowId( (byte) WindowMagicNumbers.ARMOR.getId() );
            inventory.setItems( getContents() );
            playerConnection.send( inventory );
        } else {

        }
    }

    @Override
    public void sendContents( int slot, PlayerConnection playerConnection ) {
        if ( playerConnection.getEntity().equals( this.owner ) ) {
            PacketInventorySetSlot setSlot = new PacketInventorySetSlot();
            setSlot.setSlot( slot );
            setSlot.setWindowId( (byte) WindowMagicNumbers.ARMOR.getId() );
            setSlot.setItemStack( this.contents[slot] );
            playerConnection.addToSendQueue( setSlot );
        } else {

        }
    }

}
