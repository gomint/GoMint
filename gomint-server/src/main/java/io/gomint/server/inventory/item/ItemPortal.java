package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 90 )
 public class ItemPortal extends ItemStack implements io.gomint.inventory.item.ItemPortal {

    // CHECKSTYLE:OFF
    public ItemPortal( short data, int amount ) {
        super( 90, data, amount );
    }

    public ItemPortal( short data, int amount, NBTTagCompound nbt ) {
        super( 90, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
