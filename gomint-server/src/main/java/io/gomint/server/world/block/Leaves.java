package io.gomint.server.world.block;

import io.gomint.world.block.BlockType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockLeaves;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 18 )
public class Leaves extends Block implements BlockLeaves {

    @Override
    public int getBlockId() {
        return 18;
    }

    @Override
    public long getBreakTime() {
        return 300;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }
    @Override
    public float getBlastResistance() {
        return 1.0f;
    }

    @Override
    public BlockType getType() {
        return BlockType.LEAVES;
    }

}