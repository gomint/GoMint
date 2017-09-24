package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 332 )
 public class ItemSnowball extends ItemStack implements io.gomint.inventory.item.ItemSnowball {

    // CHECKSTYLE:OFF
    public ItemSnowball( short data, int amount ) {
        super( 332, data, amount );
    }

    public ItemSnowball( short data, int amount, NBTTagCompound nbt ) {
        super( 332, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
