package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 95 )
 public class ItemInvisibleBedrock extends ItemStack implements io.gomint.inventory.item.ItemInvisibleBedrock {

    // CHECKSTYLE:OFF
    public ItemInvisibleBedrock( short data, int amount ) {
        super( 95, data, amount );
    }

    public ItemInvisibleBedrock( short data, int amount, NBTTagCompound nbt ) {
        super( 95, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
