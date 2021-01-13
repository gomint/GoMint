package io.gomint.server.world.block;

import io.gomint.world.block.BlockRedMushroomBlock;
import io.gomint.world.block.BlockType;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:red_mushroom_block" )
public class RedMushroomBlock extends Block implements BlockRedMushroomBlock {

    @Override
    public String getBlockId() {
        return "minecraft:red_mushroom_block";
    }

    @Override
    public float getBlastResistance() {
        return 1.0f;
    }

    @Override
    public BlockType blockType() {
        return BlockType.RED_MUSHROOM_BLOCK;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

}
