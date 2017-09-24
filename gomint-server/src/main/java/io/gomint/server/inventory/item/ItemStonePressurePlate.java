package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 70 )
 public class ItemStonePressurePlate extends ItemStack implements io.gomint.inventory.item.ItemStonePressurePlate {

    // CHECKSTYLE:OFF
    public ItemStonePressurePlate( short data, int amount ) {
        super( 70, data, amount );
    }

    public ItemStonePressurePlate( short data, int amount, NBTTagCompound nbt ) {
        super( 70, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
