package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 345 )
 public class ItemCompass extends ItemStack implements io.gomint.inventory.item.ItemCompass {

    // CHECKSTYLE:OFF
    public ItemCompass( short data, int amount ) {
        super( 345, data, amount );
    }

    public ItemCompass( short data, int amount, NBTTagCompound nbt ) {
        super( 345, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public ItemType getType() {
        return ItemType.COMPASS;
    }

}