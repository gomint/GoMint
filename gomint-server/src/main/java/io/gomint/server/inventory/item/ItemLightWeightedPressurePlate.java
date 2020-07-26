package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:light_weighted_pressure_plate", id = 147)
public class ItemLightWeightedPressurePlate extends ItemStack implements io.gomint.inventory.item.ItemLightWeightedPressurePlate {

    @Override
    public ItemType getItemType() {
        return ItemType.LIGHT_WEIGHTED_PRESSURE_PLATE;
    }

}
