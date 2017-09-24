package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 29 )
 public class ItemStickyPiston extends ItemStack implements io.gomint.inventory.item.ItemStickyPiston {

    // CHECKSTYLE:OFF
    public ItemStickyPiston( short data, int amount ) {
        super( 29, data, amount );
    }

    public ItemStickyPiston( short data, int amount, NBTTagCompound nbt ) {
        super( 29, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
