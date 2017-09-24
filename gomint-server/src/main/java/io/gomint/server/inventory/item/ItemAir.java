package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 0 )
 public class ItemAir extends ItemStack implements io.gomint.inventory.item.ItemAir {

    // CHECKSTYLE:OFF
    public ItemAir( short data, int amount ) {
        super( 0, data, amount );
    }

    public ItemAir( short data, int amount, NBTTagCompound nbt ) {
        super( 0, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
