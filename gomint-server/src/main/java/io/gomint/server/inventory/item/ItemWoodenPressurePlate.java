package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

import java.time.Duration;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:wooden_pressure_plate", id = 72)
public class ItemWoodenPressurePlate extends ItemStack< io.gomint.inventory.item.ItemWoodenPressurePlate> implements io.gomint.inventory.item.ItemWoodenPressurePlate {

    @Override
    public Duration burnTime() {
        return Duration.ofMillis(15000);
    }

    @Override
    public ItemType itemType() {
        return ItemType.WOODEN_PRESSURE_PLATE;
    }

}
