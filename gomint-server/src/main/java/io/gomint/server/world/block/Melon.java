package io.gomint.server.world.block;

import io.gomint.world.block.BlockType;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 103 )
public class Melon extends Block implements io.gomint.world.block.BlockMelon {

    @Override
    public int getBlockId() {
        return 103;
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
        return BlockType.MELON;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

}
