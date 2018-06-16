package io.gomint.server.world.block;

import io.gomint.world.block.BlockType;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 177 )
public class WallBanner extends Banner {

    @Override
    public int getBlockId() {
        return 177;
    }

    @Override
    public BlockType getType() {
        return BlockType.WALL_BANNER;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

}
