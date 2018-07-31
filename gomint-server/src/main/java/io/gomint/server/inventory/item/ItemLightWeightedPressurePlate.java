package io.gomint.server.inventory.item;
import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 147 )
 public class ItemLightWeightedPressurePlate extends ItemStack implements io.gomint.inventory.item.ItemLightWeightedPressurePlate {



    @Override
    public ItemType getType() {
        return ItemType.LIGHT_WEIGHTED_PRESSURE_PLATE;
    }

}
