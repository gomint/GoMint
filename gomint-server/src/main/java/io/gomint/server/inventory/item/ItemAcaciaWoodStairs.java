package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

import java.time.Duration;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:acacia_stairs", id = 163)
public class ItemAcaciaWoodStairs extends ItemStack<io.gomint.inventory.item.ItemAcaciaWoodStairs> implements io.gomint.inventory.item.ItemAcaciaWoodStairs {

    @Override
    public ItemType itemType() {
        return ItemType.ACACIA_WOOD_STAIRS;
    }

    @Override
    public Duration burnTime() {
        return Duration.ofMillis(15000);
    }

}
