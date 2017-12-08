package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 236 )
 public class ItemConcrete extends ItemStack implements io.gomint.inventory.item.ItemConcrete {

    // CHECKSTYLE:OFF
    public ItemConcrete( short data, int amount ) {
        super( 236, data, amount );
    }

    public ItemConcrete( short data, int amount, NBTTagCompound nbt ) {
        super( 236, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public ItemType getType() {
        return ItemType.CONCRETE;
    }

}