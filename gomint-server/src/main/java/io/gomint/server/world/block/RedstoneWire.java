package io.gomint.server.world.block;

import io.gomint.world.block.BlockType;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 55 )
public class RedstoneWire extends Block implements io.gomint.world.block.BlockRedstoneWire {

    @Override
    public int getBlockId() {
        return 55;
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
        return BlockType.REDSTONE_WIRE;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

}
