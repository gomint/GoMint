package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockDarmstadtium;
import io.gomint.world.block.BlockType;

/**
 * @author Kaooot
 * @version 1.0
 */
@RegisterInfo( id = 376 )
public class Darmstadtium extends BlockAtom implements BlockDarmstadtium {

    @Override
    public int getBlockId() {
        return 376;
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
        return BlockType.DARMSTADTIUM;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public int getProtons() {
        return 110;
    }

    @Override
    public int getElectrons() {
        return 110;
    }

    @Override
    public int getNeutrons() {
        return 171;
    }

    @Override
    public int getAtomicWeight() {
        return 281;
    }
}
