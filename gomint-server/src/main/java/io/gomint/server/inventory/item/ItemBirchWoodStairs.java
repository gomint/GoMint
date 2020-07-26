package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:birch_stairs", id = 135)
public class ItemBirchWoodStairs extends ItemStack implements io.gomint.inventory.item.ItemBirchWoodStairs {

    @Override
    public ItemType getItemType() {
        return ItemType.BIRCH_WOOD_STAIRS;
    }

}
