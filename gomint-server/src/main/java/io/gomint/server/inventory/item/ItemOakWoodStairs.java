package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

import java.time.Duration;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:oak_stairs")
public class ItemOakWoodStairs extends ItemStack<io.gomint.inventory.item.ItemOakWoodStairs> implements io.gomint.inventory.item.ItemOakWoodStairs {

    @Override
    public ItemType itemType() {
        return ItemType.OAK_WOOD_STAIRS;
    }

    @Override
    public Duration burnTime() {
        return Duration.ofMillis(15000);
    }

}
