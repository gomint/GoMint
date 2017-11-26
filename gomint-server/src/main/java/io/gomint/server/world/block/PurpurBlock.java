package io.gomint.server.world.block;

import io.gomint.world.block.BlockType;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 201 )
public class PurpurBlock extends Block implements io.gomint.world.block.BlockPurpurBlock {

    @Override
    public int getBlockId() {
        return 201;
    }

    @Override
    public long getBreakTime() {
        return 2250;
    }

    @Override
    public float getBlastResistance() {
        return 30.0f;
    }

    @Override
    public BlockType getType() {
        return BlockType.PURPUR_BLOCK;
    }

}