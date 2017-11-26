package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 41 )
 public class ItemBlockOfGold extends ItemStack implements io.gomint.inventory.item.ItemBlockOfGold {

    // CHECKSTYLE:OFF
    public ItemBlockOfGold( short data, int amount ) {
        super( 41, data, amount );
    }

    public ItemBlockOfGold( short data, int amount, NBTTagCompound nbt ) {
        super( 41, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public ItemType getType() {
        return ItemType.BLOCK_OF_GOLD;
    }

}