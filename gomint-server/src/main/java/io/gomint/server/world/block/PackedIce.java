package io.gomint.server.world.block;

import io.gomint.world.block.BlockType;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 174 )
public class PackedIce extends Block implements io.gomint.world.block.BlockPackedIce {

    @Override
    public int getBlockId() {
        return 174;
    }

    @Override
    public long getBreakTime() {
        return 750;
    }

    @Override
    public float getBlastResistance() {
        return 2.5f;
    }

    @Override
    public BlockType getType() {
        return BlockType.PACKED_ICE;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

}
