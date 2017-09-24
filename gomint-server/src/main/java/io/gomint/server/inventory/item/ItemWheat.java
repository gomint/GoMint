package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 296 )
 public class ItemWheat extends ItemStack implements io.gomint.inventory.item.ItemWheat {

    // CHECKSTYLE:OFF
    public ItemWheat( short data, int amount ) {
        super( 296, data, amount );
    }

    public ItemWheat( short data, int amount, NBTTagCompound nbt ) {
        super( 296, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
