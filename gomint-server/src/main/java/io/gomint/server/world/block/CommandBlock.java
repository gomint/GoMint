package io.gomint.server.world.block;

import io.gomint.world.block.BlockType;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 137 )
public class CommandBlock extends Block implements io.gomint.world.block.BlockCommandBlock {

    @Override
    public int getBlockId() {
        return 137;
    }

    @Override
    public float getBlastResistance() {
        return 1.8E7f;
    }

    @Override
    public BlockType getType() {
        return BlockType.COMMAND_BLOCK;
    }

}