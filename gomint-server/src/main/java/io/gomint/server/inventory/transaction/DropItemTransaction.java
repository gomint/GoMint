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
public class DropItemTransaction<T> implements Transaction<Void, Void, T> {

    private final Location location;
    private final Vector velocity;
    private final ItemStack<T> targetItem;

    public DropItemTransaction(Location location, Vector velocity, ItemStack<T> targetItem) {
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
    public ItemStack<T> targetItem() {
        return targetItem;
    }

    @Override
    public boolean hasInventory() {
        return false;
    }

    @Override
    public ItemStack<Void> sourceItem() {
        return null;
    }

    @Override
    public Inventory<Void> inventory() {
        return null;
    }

    @Override
    public int slot() {
        return -1;
    }

    @Override
    public void commit() {
        EntityItem item = (EntityItem) this.location.world().createItemDrop( this.location, this.targetItem );
        item.velocity( this.velocity );
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
