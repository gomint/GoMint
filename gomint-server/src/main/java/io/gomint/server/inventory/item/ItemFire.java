package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 51 )
 public class ItemFire extends ItemStack implements io.gomint.inventory.item.ItemFire {

    // CHECKSTYLE:OFF
    public ItemFire( short data, int amount ) {
        super( 51, data, amount );
    }

    public ItemFire( short data, int amount, NBTTagCompound nbt ) {
        super( 51, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
