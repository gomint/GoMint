package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 85 )
 public class ItemFence extends ItemStack implements io.gomint.inventory.item.ItemFence {

    // CHECKSTYLE:OFF
    public ItemFence( short data, int amount ) {
        super( 85, data, amount );
    }

    public ItemFence( short data, int amount, NBTTagCompound nbt ) {
        super( 85, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
