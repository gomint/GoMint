package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 44 )
 public class ItemStoneSlab extends ItemStack implements io.gomint.inventory.item.ItemStoneSlab {

    // CHECKSTYLE:OFF
    public ItemStoneSlab( short data, int amount ) {
        super( 44, data, amount );
    }

    public ItemStoneSlab( short data, int amount, NBTTagCompound nbt ) {
        super( 44, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public ItemType getType() {
        return ItemType.STONE_SLAB;
    }

}