package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 369 )
 public class ItemBlazeRod extends ItemStack implements io.gomint.inventory.item.ItemBlazeRod {

    // CHECKSTYLE:OFF
    public ItemBlazeRod( short data, int amount ) {
        super( 369, data, amount );
    }

    public ItemBlazeRod( short data, int amount, NBTTagCompound nbt ) {
        super( 369, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
