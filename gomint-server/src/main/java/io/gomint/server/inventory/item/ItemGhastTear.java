package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 370 )
 public class ItemGhastTear extends ItemStack implements io.gomint.inventory.item.ItemGhastTear {

    // CHECKSTYLE:OFF
    public ItemGhastTear( short data, int amount ) {
        super( 370, data, amount );
    }

    public ItemGhastTear( short data, int amount, NBTTagCompound nbt ) {
        super( 370, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public ItemType getType() {
        return ItemType.GHAST_TEAR;
    }

}