package io.gomint.server.world.block;

import io.gomint.world.block.BlockType;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 208 )
public class EndRod extends Block implements io.gomint.world.block.BlockEndRod {

    @Override
    public int getBlockId() {
        return 208;
    }

    @Override
    public float getBlastResistance() {
        return 0.0f;
    }

    @Override
    public BlockType getType() {
        return BlockType.END_ROD;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

}
