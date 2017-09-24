package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 148 )
 public class ItemHeavyWeightedPressurePlate extends ItemStack implements io.gomint.inventory.item.ItemHeavyWeightedPressurePlate {

    // CHECKSTYLE:OFF
    public ItemHeavyWeightedPressurePlate( short data, int amount ) {
        super( 148, data, amount );
    }

    public ItemHeavyWeightedPressurePlate( short data, int amount, NBTTagCompound nbt ) {
        super( 148, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
