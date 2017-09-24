package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 325 )
 public class ItemBucket extends ItemStack implements io.gomint.inventory.item.ItemBucket {

    // CHECKSTYLE:OFF
    public ItemBucket( short data, int amount ) {
        super( 325, data, amount );
    }

    public ItemBucket( short data, int amount, NBTTagCompound nbt ) {
        super( 325, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
