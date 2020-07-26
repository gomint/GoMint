package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:dark_oak_stairs", id = 164)
public class ItemDarkOakWoodStairs extends ItemStack implements io.gomint.inventory.item.ItemDarkOakWoodStairs {

    @Override
    public ItemType getItemType() {
        return ItemType.DARK_OAK_WOOD_STAIRS;
    }

}
