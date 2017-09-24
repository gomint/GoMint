package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 147 )
 public class ItemLightWeightedPressurePlate extends ItemStack implements io.gomint.inventory.item.ItemLightWeightedPressurePlate {

    // CHECKSTYLE:OFF
    public ItemLightWeightedPressurePlate( short data, int amount ) {
        super( 147, data, amount );
    }

    public ItemLightWeightedPressurePlate( short data, int amount, NBTTagCompound nbt ) {
        super( 147, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
