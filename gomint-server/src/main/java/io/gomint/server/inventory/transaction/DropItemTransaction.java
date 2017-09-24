package io.gomint.server.inventory.transaction;

import io.gomint.inventory.item.ItemStack;
import io.gomint.math.Location;
import io.gomint.math.Vector;
import io.gomint.server.entity.passive.EntityItem;
import io.gomint.server.inventory.Inventory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * @author geNAZt
 * @version 1.0
 */
@AllArgsConstructor
@Getter
@ToString
public class DropItemTransaction implements Transaction {

    private final Location location;
    private final Vector velocity;
    private final ItemStack targetItem;

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

}
