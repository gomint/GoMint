package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 23 )
 public class ItemDispenser extends ItemStack implements io.gomint.inventory.item.ItemDispenser {

    // CHECKSTYLE:OFF
    public ItemDispenser( short data, int amount ) {
        super( 23, data, amount );
    }

    public ItemDispenser( short data, int amount, NBTTagCompound nbt ) {
        super( 23, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
