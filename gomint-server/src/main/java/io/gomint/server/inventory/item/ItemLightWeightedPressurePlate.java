package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:light_weighted_pressure_plate")
public class ItemLightWeightedPressurePlate extends ItemStack< io.gomint.inventory.item.ItemLightWeightedPressurePlate> implements io.gomint.inventory.item.ItemLightWeightedPressurePlate {

    @Override
    public ItemType itemType() {
        return ItemType.LIGHT_WEIGHTED_PRESSURE_PLATE;
    }

}
