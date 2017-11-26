package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 120 )
 public class ItemEndPortalFrame extends ItemStack implements io.gomint.inventory.item.ItemEndPortalFrame {

    // CHECKSTYLE:OFF
    public ItemEndPortalFrame( short data, int amount ) {
        super( 120, data, amount );
    }

    public ItemEndPortalFrame( short data, int amount, NBTTagCompound nbt ) {
        super( 120, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public ItemType getType() {
        return ItemType.END_PORTAL_FRAME;
    }

}