package io.gomint.server.world.block;

import io.gomint.world.block.BlockType;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 40 )
public class RedMushroom extends Block implements io.gomint.world.block.BlockRedMushroom {

    @Override
    public int getBlockId() {
        return 40;
    }

    @Override
    public float getBlastResistance() {
        return 0.0f;
    }

    @Override
    public BlockType getType() {
        return BlockType.RED_MUSHROOM;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

}
