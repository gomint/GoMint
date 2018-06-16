package io.gomint.server.world.block;

import io.gomint.world.block.BlockType;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 86 )
public class Pumpkin extends Block implements io.gomint.world.block.BlockPumpkin {

    @Override
    public int getBlockId() {
        return 86;
    }

    @Override
    public long getBreakTime() {
        return 1500;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    @Override
    public float getBlastResistance() {
        return 5.0f;
    }

    @Override
    public BlockType getType() {
        return BlockType.PUMPKIN;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

}
