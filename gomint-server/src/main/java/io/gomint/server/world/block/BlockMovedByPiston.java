package io.gomint.server.world.block;

import io.gomint.world.block.BlockType;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 250 )
public class BlockMovedByPiston extends Block implements io.gomint.world.block.BlockBlockMovedByPiston {

    @Override
    public int getBlockId() {
        return 250;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    @Override
    public float getBlastResistance() {
        return 0.0f;
    }

    @Override
    public BlockType getType() {
        return BlockType.BLOCK_MOVED_BY_PISTON;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

}
