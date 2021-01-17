package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:stone_brick_stairs")
public class ItemStoneBrickStairs extends ItemStack< io.gomint.inventory.item.ItemStoneBrickStairs> implements io.gomint.inventory.item.ItemStoneBrickStairs {

    @Override
    public ItemType itemType() {
        return ItemType.STONE_BRICK_STAIRS;
    }

}
