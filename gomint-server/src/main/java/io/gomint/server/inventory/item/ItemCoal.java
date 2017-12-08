package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 263 )
 public class ItemCoal extends ItemStack implements io.gomint.inventory.item.ItemCoal {

    // CHECKSTYLE:OFF
    public ItemCoal( short data, int amount ) {
        super( 263, data, amount );
    }

    public ItemCoal( short data, int amount, NBTTagCompound nbt ) {
        super( 263, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public ItemType getType() {
        return ItemType.COAL;
    }

}