package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockDubnium;
import io.gomint.world.block.BlockType;

/**
 * @author Kaooot
 * @version 1.0
 */
@RegisterInfo( id = 371 )
public class Dubnium extends BlockAtom implements BlockDubnium {

    @Override
    public int getBlockId() {
        return 371;
    }

    @Override
    public long getBreakTime() {
        return 0;
    }

    @Override
    public float getBlastResistance() {
        return 0;
    }

    @Override
    public BlockType getType() {
        return BlockType.DUBNIUM;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public int getProtons() {
        return 105;
    }

    @Override
    public int getElectrons() {
        return 105;
    }

    @Override
    public int getNeutrons() {
        return 163;
    }

    @Override
    public int getAtomicWeight() {
        return 268;
    }
}
