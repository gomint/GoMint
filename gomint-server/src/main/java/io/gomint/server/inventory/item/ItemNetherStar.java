package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 399 )
 public class ItemNetherStar extends ItemStack implements io.gomint.inventory.item.ItemNetherStar {

    // CHECKSTYLE:OFF
    public ItemNetherStar( short data, int amount ) {
        super( 399, data, amount );
    }

    public ItemNetherStar( short data, int amount, NBTTagCompound nbt ) {
        super( 399, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
