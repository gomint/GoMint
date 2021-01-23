package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemStack;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.block.helper.ToolPresets;
import io.gomint.world.block.BlockPrismarineWall;
import io.gomint.world.block.BlockType;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:cobblestone_wall[wall_block_type=prismarine]")
public class PrismarineWall extends Wall<BlockPrismarineWall> implements BlockPrismarineWall {

    @Override
    public long breakTime() {
        return 2250;
    }

    @Override
    public float blastResistance() {
        return 30.0f;
    }

    @Override
    public BlockType blockType() {
        return BlockType.PRISMARINE;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public Class<? extends ItemStack<?>>[] toolInterfaces() {
        return ToolPresets.PICKAXE;
    }


}
