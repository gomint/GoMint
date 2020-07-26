package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:spruce_stairs", id = 134)
public class ItemSpruceWoodStairs extends ItemStack implements io.gomint.inventory.item.ItemSpruceWoodStairs {

    @Override
    public ItemType getItemType() {
        return ItemType.SPRUCE_WOOD_STAIRS;
    }

}
