package io.gomint.server.world.block;

import io.gomint.world.block.BlockType;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 90 )
public class Portal extends Block implements io.gomint.world.block.BlockPortal {

    @Override
    public int getBlockId() {
        return 90;
    }

    @Override
    public long getBreakTime() {
        return -1;
    }

    @Override
    public float getBlastResistance() {
        return 0.0f;
    }

    @Override
    public BlockType getType() {
        return BlockType.PORTAL;
    }

}
