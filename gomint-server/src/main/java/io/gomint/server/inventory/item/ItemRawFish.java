package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 349 )
 public class ItemRawFish extends ItemStack implements io.gomint.inventory.item.ItemRawFish {

    // CHECKSTYLE:OFF
    public ItemRawFish( short data, int amount ) {
        super( 349, data, amount );
    }

    public ItemRawFish( short data, int amount, NBTTagCompound nbt ) {
        super( 349, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
