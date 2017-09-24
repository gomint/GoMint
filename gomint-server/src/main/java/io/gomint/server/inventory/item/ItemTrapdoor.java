package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 96 )
 public class ItemTrapdoor extends ItemStack implements io.gomint.inventory.item.ItemTrapdoor {

    // CHECKSTYLE:OFF
    public ItemTrapdoor( short data, int amount ) {
        super( 96, data, amount );
    }

    public ItemTrapdoor( short data, int amount, NBTTagCompound nbt ) {
        super( 96, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
