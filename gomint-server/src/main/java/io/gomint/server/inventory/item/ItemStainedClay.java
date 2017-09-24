package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 159 )
 public class ItemStainedClay extends ItemStack implements io.gomint.inventory.item.ItemStainedClay {

    // CHECKSTYLE:OFF
    public ItemStainedClay( short data, int amount ) {
        super( 159, data, amount );
    }

    public ItemStainedClay( short data, int amount, NBTTagCompound nbt ) {
        super( 159, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
