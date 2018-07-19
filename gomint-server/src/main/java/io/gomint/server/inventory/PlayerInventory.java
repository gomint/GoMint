package io.gomint.server.inventory;

import io.gomint.entity.Entity;
import io.gomint.inventory.InventoryType;
import io.gomint.inventory.item.ItemStack;
import io.gomint.server.entity.EntityPlayer;
import io.gomint.server.entity.passive.EntityHuman;
import io.gomint.server.network.PlayerConnection;
import io.gomint.server.network.packet.PacketInventoryContent;
import io.gomint.server.network.packet.PacketInventorySetSlot;
import io.gomint.server.network.packet.PacketMobEquipment;

/**
 * @author geNAZt
 * @version 1.0
 */
public class PlayerInventory extends Inventory implements io.gomint.inventory.PlayerInventory {

    private byte itemInHandSlot;

    /**
     * Construct a new Inventory for the player which is 36 + 9 in size
     *
     * @param player for which this inventory is
     */
    public PlayerInventory( EntityHuman player ) {
        super( player, 36 );
    }

    /**
     * Get the item this player is currently holding
     *
     * @return the itemstack the player is holding
     */
    public ItemStack getItemInHand() {
        return this.contents[this.itemInHandSlot];
    }

    @Override
    public void setItem( int index, ItemStack item ) {
        ItemStack oldItem = getItem( index );
        super.setItem( index, item );

        if ( index == this.itemInHandSlot && this.owner instanceof EntityPlayer ) {
            // Inform the old item it got deselected
            io.gomint.server.inventory.item.ItemStack oldItemInHand = (io.gomint.server.inventory.item.ItemStack) oldItem;
            oldItemInHand.removeFromHand( (EntityPlayer) this.owner );

            // Inform the item it got selected
            io.gomint.server.inventory.item.ItemStack newItemInHand = (io.gomint.server.inventory.item.ItemStack) item;
            newItemInHand.gotInHand( (EntityPlayer) this.owner );

            // Update the item for everyone else
            this.updateItemInHand();
        }
    }

    @Override
    public void sendContents( int slot, PlayerConnection playerConnection ) {
        PacketInventorySetSlot setSlot = new PacketInventorySetSlot();
        setSlot.setSlot( slot );
        setSlot.setWindowId( WindowMagicNumbers.PLAYER.getId() );
        setSlot.setItemStack( this.contents[slot] );
        playerConnection.addToSendQueue( setSlot );
    }

    @Override
    public void sendContents( PlayerConnection playerConnection ) {
        PacketInventoryContent inventory = new PacketInventoryContent();
        inventory.setWindowId( WindowMagicNumbers.PLAYER.getId() );
        inventory.setItems( getContentsArray() );
        playerConnection.addToSendQueue( inventory );
    }

    /**
     * Set the slot for the item the player currently has in hand
     *
     * @param slot in the inventory to point on the item in hand
     */
    public void setItemInHand( byte slot ) {
        if ( this.owner instanceof EntityPlayer ) {
            this.updateItemInHandWithItem( slot );
        }

        this.updateItemInHand();
    }

    private void updateItemInHand() {
        EntityHuman player = (EntityHuman) this.owner;

        PacketMobEquipment packet = new PacketMobEquipment();
        packet.setEntityId( player.getEntityId() );
        packet.setStack( this.getItemInHand() );
        packet.setWindowId( (byte) 0 );
        packet.setSelectedSlot( this.itemInHandSlot );
        packet.setSlot( this.itemInHandSlot );

        // Relay packet
        for ( Entity entity : player.getAttachedEntities() ) {
            if ( entity instanceof EntityPlayer ) {
                ( (EntityPlayer) entity ).getConnection().addToSendQueue( packet );
            }
        }
    }

    /**
     * Get the number of the slot the user is currently holding in hand
     *
     * @return the slot number for the in hand item
     */
    public byte getItemInHandSlot() {
        return this.itemInHandSlot;
    }

    @Override
    public void setItemInHandSlot( byte slot ) {
        if ( slot > 8 || slot < 0 ) {
            return;
        }

        this.itemInHandSlot = slot;
        this.updateItemInHand();
    }

    public void updateItemInHandWithItem( byte slot ) {
        // Inform the old item it got deselected
        io.gomint.server.inventory.item.ItemStack oldItemInHand = (io.gomint.server.inventory.item.ItemStack) this.getItemInHand();
        oldItemInHand.removeFromHand( (EntityPlayer) this.owner );

        // Set item in hand index
        this.itemInHandSlot = slot;

        // Inform the item it got selected
        io.gomint.server.inventory.item.ItemStack newItemInHand =
            (io.gomint.server.inventory.item.ItemStack) this.getItemInHand();
        newItemInHand.gotInHand( (EntityPlayer) this.owner );
    }

    @Override
    protected void onRemove( int slot ) {
        super.onRemove( slot );

        if ( slot == this.itemInHandSlot && this.owner instanceof EntityPlayer ) {
            // Inform the old item it got deselected
            io.gomint.server.inventory.item.ItemStack oldItemInHand = (io.gomint.server.inventory.item.ItemStack) this.getItem( slot );
            oldItemInHand.removeFromHand( (EntityPlayer) this.owner );
        }
    }

    @Override
    public InventoryType getInventoryType() {
        return InventoryType.PLAYER;
    }

}
