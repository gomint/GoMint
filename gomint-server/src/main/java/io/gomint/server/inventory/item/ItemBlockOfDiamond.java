package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 57 )
 public class ItemBlockOfDiamond extends ItemStack implements io.gomint.inventory.item.ItemBlockOfDiamond {

    // CHECKSTYLE:OFF
    public ItemBlockOfDiamond( short data, int amount ) {
        super( 57, data, amount );
    }

    public ItemBlockOfDiamond( short data, int amount, NBTTagCompound nbt ) {
        super( 57, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public ItemType getType() {
        return ItemType.BLOCK_OF_DIAMOND;
    }

}