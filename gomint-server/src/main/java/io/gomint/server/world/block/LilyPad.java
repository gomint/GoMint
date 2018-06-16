package io.gomint.server.world.block;

import io.gomint.world.block.BlockType;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 111 )
public class LilyPad extends Block implements io.gomint.world.block.BlockLilyPad {

    @Override
    public int getBlockId() {
        return 111;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    @Override
    public boolean isSolid() {
        return false;
    }

    @Override
    public float getBlastResistance() {
        return 0.0f;
    }

    @Override
    public BlockType getType() {
        return BlockType.LILY_PAD;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

}
