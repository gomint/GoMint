package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 72 )
 public class ItemWoodenPressurePlate extends ItemStack implements io.gomint.inventory.item.ItemWoodenPressurePlate {

    // CHECKSTYLE:OFF
    public ItemWoodenPressurePlate( short data, int amount ) {
        super( 72, data, amount );
    }

    public ItemWoodenPressurePlate( short data, int amount, NBTTagCompound nbt ) {
        super( 72, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
