package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 148 )
 public class ItemWeightedPressurePlateHeavy extends ItemStack implements io.gomint.inventory.item.ItemWeightedPressurePlateHeavy {

    // CHECKSTYLE:OFF
    public ItemWeightedPressurePlateHeavy( short data, int amount ) {
        super( 148, data, amount );
    }

    public ItemWeightedPressurePlateHeavy( short data, int amount, NBTTagCompound nbt ) {
        super( 148, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
