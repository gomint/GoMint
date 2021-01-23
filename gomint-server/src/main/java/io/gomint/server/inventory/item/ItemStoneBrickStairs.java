package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;
import io.gomint.world.block.Block;
import io.gomint.world.block.BlockStoneBrickStairs;
import io.gomint.world.block.data.StoneBrickStairsType;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:stone_brick_stairs", def = true )
@RegisterInfo(sId = "minecraft:mossy_stone_brick_stairs")
public class ItemStoneBrickStairs extends ItemStack< io.gomint.inventory.item.ItemStoneBrickStairs> implements io.gomint.inventory.item.ItemStoneBrickStairs {

    @Override
    public ItemType itemType() {
        return ItemType.STONE_BRICK_STAIRS;
    }

    @Override
    public io.gomint.inventory.item.ItemStoneBrickStairs type(StoneBrickStairsType type) {
        return type == StoneBrickStairsType.NORMAL ? this.material("minecraft:stone_brick_stairs") :
            this.material("minecraft:mossy_stone_brick_stairs");
    }

    @Override
    public StoneBrickStairsType type() {
        return "minecraft:mossy_stone_brick_stairs".equals(this.material()) ? StoneBrickStairsType.MOSSY : StoneBrickStairsType.NORMAL;
    }

}
