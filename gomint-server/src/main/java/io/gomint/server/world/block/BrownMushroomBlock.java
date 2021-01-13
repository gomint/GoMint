package io.gomint.server.world.block;

import io.gomint.world.block.BlockBrownMushroomBlock;
import io.gomint.world.block.BlockType;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:brown_mushroom_block" )
public class BrownMushroomBlock extends Block implements BlockBrownMushroomBlock {

    @Override
    public String getBlockId() {
        return "minecraft:brown_mushroom_block";
    }

    @Override
    public float getBlastResistance() {
        return 1.0f;
    }

    @Override
    public BlockType blockType() {
        return BlockType.BROWN_MUSHROOM_BLOCK;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

}
