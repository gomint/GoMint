package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:jungle_stairs", id = 136)
public class ItemJungleWoodStairs extends ItemStack implements io.gomint.inventory.item.ItemJungleWoodStairs {

    @Override
    public ItemType getItemType() {
        return ItemType.JUNGLE_WOOD_STAIRS;
    }

}
