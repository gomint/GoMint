package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 381 )
 public class ItemEyeOfEnder extends ItemStack implements io.gomint.inventory.item.ItemEyeOfEnder {

    // CHECKSTYLE:OFF
    public ItemEyeOfEnder( short data, int amount ) {
        super( 381, data, amount );
    }

    public ItemEyeOfEnder( short data, int amount, NBTTagCompound nbt ) {
        super( 381, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public ItemType getType() {
        return ItemType.EYE_OF_ENDER;
    }

}