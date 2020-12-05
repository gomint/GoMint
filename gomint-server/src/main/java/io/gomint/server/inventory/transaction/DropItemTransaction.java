package io.gomint.server.inventory.transaction;

import io.gomint.inventory.item.ItemStack;
import io.gomint.math.Location;
import io.gomint.math.Vector;
import io.gomint.server.entity.passive.EntityItem;
import io.gomint.server.inventory.Inventory;

/**
 * @author geNAZt
 * @version 1.0
 */
public class DropItemTransaction implements Transaction {

    private final Location location;
    private final Vector velocity;
    private final ItemStack targetItem;

    public DropItemTransaction(Location location, Vector velocity, ItemStack targetItem) {
        this.location = location;
        this.velocity = velocity;
        this.targetItem = targetItem;
    }

    public Location getLocation() {
        return location;
    }

    public Vector getVelocity() {
        return velocity;
    }

    @Override
    public ItemStack getTargetItem() {
        return targetItem;
    }

    @Override
    public boolean hasInventory() {
        return false;
    }

    @Override
    public ItemStack getSourceItem() {
        return null;
    }

    @Override
    public Inventory getInventory() {
        return null;
    }

    @Override
    public int getSlot() {
        return -1;
    }

    @Override
    public void commit() {
        EntityItem item = (EntityItem) this.location.getWorld().createItemDrop( this.location, this.targetItem );
        item.setVelocity( this.velocity );
    }

    @Override
    public void revert() {

    }

    @Override
    public byte getInventoryWindowId() {
        return 0;
    }

    @Override
    public String toString() {
        return "{\"_class\":\"DropItemTransaction\", " +
            "\"location\":" + (location == null ? "null" : location) + ", " +
            "\"velocity\":" + (velocity == null ? "null" : velocity) + ", " +
            "\"targetItem\":" + (targetItem == null ? "null" : targetItem) +
            "}";
    }

}
