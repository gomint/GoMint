package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 119 )
 public class ItemEndPortal extends ItemStack implements io.gomint.inventory.item.ItemEndPortal {

    // CHECKSTYLE:OFF
    public ItemEndPortal( short data, int amount ) {
        super( 119, data, amount );
    }

    public ItemEndPortal( short data, int amount, NBTTagCompound nbt ) {
        super( 119, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public ItemType getType() {
        return ItemType.END_PORTAL;
    }

}