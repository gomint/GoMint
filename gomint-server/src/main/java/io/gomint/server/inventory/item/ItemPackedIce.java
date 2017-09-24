package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 174 )
 public class ItemPackedIce extends ItemStack implements io.gomint.inventory.item.ItemPackedIce {

    // CHECKSTYLE:OFF
    public ItemPackedIce( short data, int amount ) {
        super( 174, data, amount );
    }

    public ItemPackedIce( short data, int amount, NBTTagCompound nbt ) {
        super( 174, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
