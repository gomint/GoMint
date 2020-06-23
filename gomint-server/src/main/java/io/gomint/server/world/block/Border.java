package io.gomint.server.world.block;

import io.gomint.world.block.BlockType;

public class Border extends Block implements io.gomint.world.block.BlockBorder {

    @Override
    public float getBlastResistance() {
        return 0; //TODO get it , No Information
    }

    @Override
    public BlockType getType() {
        return BlockType.BORDER;
    }
}
