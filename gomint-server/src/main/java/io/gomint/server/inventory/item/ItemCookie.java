package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 357 )
 public class ItemCookie extends ItemStack implements io.gomint.inventory.item.ItemCookie {

    // CHECKSTYLE:OFF
    public ItemCookie( short data, int amount ) {
        super( 357, data, amount );
    }

    public ItemCookie( short data, int amount, NBTTagCompound nbt ) {
        super( 357, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
