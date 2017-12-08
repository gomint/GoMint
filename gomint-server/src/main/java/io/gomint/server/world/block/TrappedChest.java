package io.gomint.server.world.block;

import io.gomint.world.block.BlockType;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 146 )
public class TrappedChest extends Block implements io.gomint.world.block.BlockTrappedChest {

    @Override
    public int getBlockId() {
        return 146;
    }

    @Override
    public long getBreakTime() {
        return 3750;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    @Override
    public float getBlastResistance() {
        return 12.5f;
    }

    @Override
    public BlockType getType() {
        return BlockType.TRAPPED_CHEST;
    }

}