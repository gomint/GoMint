package io.gomint.server.inventory.transaction;

import io.gomint.inventory.item.ItemStack;
import io.gomint.server.entity.EntityPlayer;
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
public class InventoryTransaction implements Transaction {

    private final EntityPlayer owner;
    private final Inventory inventory;
    private final int slot;
    private final ItemStack sourceItem;
    private final ItemStack targetItem;
    private final long creationTime;

    @Override
    public boolean hasInventory() {
        return true;
    }

    @Override
    public void commit() {
        this.inventory.setItem( this.slot, this.targetItem );
    }

    @Override
    public void revert() {
        this.inventory.sendContents( this.owner.getConnection() );
    }

}
