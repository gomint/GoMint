package io.gomint.server.world.block;

import io.gomint.world.block.BlockType;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 79 )
public class Ice extends Block implements io.gomint.world.block.BlockIce {

    @Override
    public int getBlockId() {
        return 79;
    }

    @Override
    public long getBreakTime() {
        return 750;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    @Override
    public float getBlastResistance() {
        return 2.5f;
    }

    @Override
    public BlockType getType() {
        return BlockType.ICE;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

}
