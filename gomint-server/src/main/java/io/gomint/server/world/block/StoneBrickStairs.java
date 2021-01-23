package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemStack;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.block.helper.ToolPresets;
import io.gomint.world.block.BlockStoneBrickStairs;
import io.gomint.world.block.BlockStoneStairs;
import io.gomint.world.block.BlockType;
import io.gomint.world.block.data.StoneBrickStairsType;
import io.gomint.world.block.data.StoneType;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:stone_brick_stairs", def = true)
@RegisterInfo(sId = "minecraft:mossy_stone_brick_stairs")
public class StoneBrickStairs extends Stairs<BlockStoneBrickStairs> implements BlockStoneBrickStairs {

    @Override
    public long breakTime() {
        return 3000;
    }

    @Override
    public float blastResistance() {
        return 30.0f;
    }

    @Override
    public BlockType blockType() {
        return BlockType.STONE_BRICK_STAIRS;
    }

    @Override
    public Class<? extends ItemStack<?>>[] toolInterfaces() {
        return ToolPresets.PICKAXE;
    }

    @Override
    public BlockStoneBrickStairs type(StoneBrickStairsType type) {
        switch (type) {
            case NORMAL:
                this.blockId("minecraft:stone_brick_stairs");
                break;
            case MOSSY:
                this.blockId("minecraft:mossy_stone_brick_stairs");
                break;
        }

        return this;
    }

    @Override
    public StoneBrickStairsType type() {
        return "minecraft:mossy_stone_brick_stairs".equals(this.blockId()) ? StoneBrickStairsType.MOSSY : StoneBrickStairsType.NORMAL;
    }

}
