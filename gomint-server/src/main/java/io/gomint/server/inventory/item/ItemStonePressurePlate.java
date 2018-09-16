package io.gomint.server.inventory.item;
import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 70 )
 public class ItemStonePressurePlate extends ItemStack implements io.gomint.inventory.item.ItemStonePressurePlate {



    @Override
    public String getBlockId() {
        return "minecraft:stone_pressure_plate";
    }

    @Override
    public ItemType getType() {
        return ItemType.STONE_PRESSURE_PLATE;
    }

}
