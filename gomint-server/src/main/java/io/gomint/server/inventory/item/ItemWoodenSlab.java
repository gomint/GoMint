package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 158 )
 public class ItemWoodenSlab extends ItemStack implements io.gomint.inventory.item.ItemWoodenSlab {

    // CHECKSTYLE:OFF
    public ItemWoodenSlab( short data, int amount ) {
        super( 158, data, amount );
    }

    public ItemWoodenSlab( short data, int amount, NBTTagCompound nbt ) {
        super( 158, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
