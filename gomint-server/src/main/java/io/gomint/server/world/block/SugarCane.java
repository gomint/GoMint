package io.gomint.server.world.block;

import io.gomint.world.block.BlockType;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 83 )
public class SugarCane extends Block implements io.gomint.world.block.BlockSugarCane {

    @Override
    public int getBlockId() {
        return 83;
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
    public long getBreakTime() {
        return 0;
    }

    @Override
    public BlockType getType() {
        return BlockType.SUGAR_CANE;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

}
