package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 88 )
 public class ItemSoulSand extends ItemStack implements io.gomint.inventory.item.ItemSoulSand {

    // CHECKSTYLE:OFF
    public ItemSoulSand( short data, int amount ) {
        super( 88, data, amount );
    }

    public ItemSoulSand( short data, int amount, NBTTagCompound nbt ) {
        super( 88, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public ItemType getType() {
        return ItemType.SOUL_SAND;
    }

}