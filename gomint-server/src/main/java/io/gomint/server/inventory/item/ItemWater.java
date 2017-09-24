package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 8 )
 public class ItemWater extends ItemStack implements io.gomint.inventory.item.ItemWater {

    // CHECKSTYLE:OFF
    public ItemWater( short data, int amount ) {
        super( 8, data, amount );
    }

    public ItemWater( short data, int amount, NBTTagCompound nbt ) {
        super( 8, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
