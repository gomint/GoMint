package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockCalifornium;
import io.gomint.world.block.BlockType;

/**
 * @author Kaooot
 * @version 1.0
 */
@RegisterInfo( id = 364 )
public class Californium extends BlockAtom implements BlockCalifornium {

    @Override
    public int getBlockId() {
        return 364;
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
        return BlockType.CALIFORNIUM;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public int getProtons() {
        return 98;
    }

    @Override
    public int getElectrons() {
        return 98;
    }

    @Override
    public int getNeutrons() {
        return 151;
    }

    @Override
    public int getAtomicWeight() {
        return 251;
    }
}
