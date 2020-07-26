package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:quartz_stairs", id = 156)
public class ItemQuartzStairs extends ItemStack implements io.gomint.inventory.item.ItemQuartzStairs {

    @Override
    public ItemType getItemType() {
        return ItemType.QUARTZ_STAIRS;
    }

}
