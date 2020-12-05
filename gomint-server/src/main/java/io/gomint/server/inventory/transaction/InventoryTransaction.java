package io.gomint.server.inventory.transaction;

import io.gomint.inventory.item.ItemStack;
import io.gomint.server.entity.EntityPlayer;
import io.gomint.server.inventory.Inventory;

/**
 * @author geNAZt
 * @version 1.0
 */
public class InventoryTransaction implements Transaction {

    private final EntityPlayer owner;
    private final Inventory inventory;
    private final int slot;
    private final ItemStack sourceItem;
    private final ItemStack targetItem;
    private final byte inventoryWindowId;

    public InventoryTransaction(EntityPlayer owner, Inventory inventory, int slot,
                                ItemStack sourceItem, ItemStack targetItem, byte inventoryWindowId) {
        this.owner = owner;
        this.inventory = inventory;
        this.slot = slot;
        this.sourceItem = sourceItem;
        this.targetItem = targetItem;
        this.inventoryWindowId = inventoryWindowId;
    }

    public EntityPlayer getOwner() {
        return owner;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public int getSlot() {
        return slot;
    }

    public ItemStack getSourceItem() {
        return sourceItem;
    }

    public ItemStack getTargetItem() {
        return targetItem;
    }

    @Override
    public boolean hasInventory() {
        return true;
    }

    @Override
    public void commit() {
        this.inventory.removeViewerWithoutAction( this.owner );
        this.inventory.setItem( this.slot, this.targetItem );
        this.inventory.addViewerWithoutAction( this.owner );
    }

    @Override
    public void revert() {
        this.inventory.sendContents( this.owner.getConnection() );
    }

    @Override
    public byte getInventoryWindowId() {
        return this.inventoryWindowId;
    }

    @Override
    public String toString() {
        return "{\"_class\":\"InventoryTransaction\", " +
            "\"owner\":" + (owner == null ? "null" : owner) + ", " +
            "\"inventory\":" + (inventory == null ? "null" : inventory) + ", " +
            "\"slot\":\"" + slot + "\"" + ", " +
            "\"sourceItem\":" + (sourceItem == null ? "null" : sourceItem) + ", " +
            "\"targetItem\":" + (targetItem == null ? "null" : targetItem) + ", " +
            "\"inventoryWindowId\":\"" + inventoryWindowId + "\"" +
            "}";
    }

}
