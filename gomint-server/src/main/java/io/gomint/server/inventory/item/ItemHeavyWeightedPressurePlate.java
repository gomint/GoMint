package io.gomint.server.inventory.item;
import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 148 )
 public class ItemHeavyWeightedPressurePlate extends ItemStack implements io.gomint.inventory.item.ItemHeavyWeightedPressurePlate {



    @Override
    public String getBlockId() {
        return "minecraft:heavy_weighted_pressure_plate";
    }

    @Override
    public ItemType getType() {
        return ItemType.HEAVY_WEIGHTED_PRESSURE_PLATE;
    }

}
