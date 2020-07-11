package io.gomint.server.world.block;

import io.gomint.world.block.BlockType;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:info_update" )
public class UpdateGameBlockUpdate1 extends Block {

    @Override
    public String getBlockId() {
        return "minecraft:info_update";
    }

    @Override
    public float getBlastResistance() {
        return 1.8E7f;
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.UPDATE_GAME_BLOCK_UPDATE1;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

}
