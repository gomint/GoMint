package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

import java.time.Duration;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:jungle_stairs")
public class ItemJungleWoodStairs extends ItemStack<io.gomint.inventory.item.ItemJungleWoodStairs> implements io.gomint.inventory.item.ItemJungleWoodStairs {

    @Override
    public ItemType itemType() {
        return ItemType.JUNGLE_WOOD_STAIRS;
    }

    @Override
    public Duration burnTime() {
        return Duration.ofMillis(15000);
    }

}
