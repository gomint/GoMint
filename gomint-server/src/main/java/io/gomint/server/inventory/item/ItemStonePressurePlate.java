package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:stone_pressure_plate")
public class ItemStonePressurePlate extends ItemStack<io.gomint.inventory.item.ItemStonePressurePlate> implements io.gomint.inventory.item.ItemStonePressurePlate {

    @Override
    public ItemType itemType() {
        return ItemType.STONE_PRESSURE_PLATE;
    }

}
