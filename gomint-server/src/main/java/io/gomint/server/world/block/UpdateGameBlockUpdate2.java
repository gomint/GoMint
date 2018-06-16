package io.gomint.server.world.block;

import io.gomint.world.block.BlockType;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 249 )
public class UpdateGameBlockUpdate2 extends Block {

    @Override
    public int getBlockId() {
        return 249;
    }

    @Override
    public float getBlastResistance() {
        return 1.8E7f;
    }

    @Override
    public BlockType getType() {
        return BlockType.UPDATE_GAME_BLOCK_UPDATE2;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

}
