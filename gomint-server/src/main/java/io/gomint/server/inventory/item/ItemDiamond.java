package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 264 )
 public class ItemDiamond extends ItemStack implements io.gomint.inventory.item.ItemDiamond {

    // CHECKSTYLE:OFF
    public ItemDiamond( short data, int amount ) {
        super( 264, data, amount );
    }

    public ItemDiamond( short data, int amount, NBTTagCompound nbt ) {
        super( 264, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public ItemType getType() {
        return ItemType.DIAMOND;
    }

}