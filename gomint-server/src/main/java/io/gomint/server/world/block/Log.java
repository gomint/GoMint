package io.gomint.server.world.block;

import io.gomint.world.block.BlockLog;
import io.gomint.world.block.BlockType;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 17 )
public class Log extends Block implements BlockLog {

    @Override
    public int getBlockId() {
        return 17;
    }

    @Override
    public long getBreakTime() {
        return 3000;
    }

    @Override
    public float getBlastResistance() {
        return 10.0f;
    }

    @Override
    public BlockType getType() {
        return BlockType.WOOD;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

}
