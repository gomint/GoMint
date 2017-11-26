package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 38 )
 public class ItemFlower extends ItemStack implements io.gomint.inventory.item.ItemFlower {

    // CHECKSTYLE:OFF
    public ItemFlower( short data, int amount ) {
        super( 38, data, amount );
    }

    public ItemFlower( short data, int amount, NBTTagCompound nbt ) {
        super( 38, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public ItemType getType() {
        return ItemType.FLOWER;
    }

}