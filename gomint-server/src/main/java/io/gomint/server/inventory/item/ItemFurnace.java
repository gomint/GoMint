package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 61 )
 public class ItemFurnace extends ItemStack implements io.gomint.inventory.item.ItemFurnace {

    // CHECKSTYLE:OFF
    public ItemFurnace( short data, int amount ) {
        super( 61, data, amount );
    }

    public ItemFurnace( short data, int amount, NBTTagCompound nbt ) {
        super( 61, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public ItemType getType() {
        return ItemType.FURNACE;
    }

}