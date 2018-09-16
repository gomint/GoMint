package io.gomint.server.world.block;

import io.gomint.world.block.BlockType;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:info_update2" )
public class UpdateGameBlockUpdate2 extends Block {

    @Override
    public String getBlockId() {
        return "minecraft:info_update2";
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
