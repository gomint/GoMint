package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockDysprosium;
import io.gomint.world.block.BlockType;

/**
 * @author Kaooot
 * @version 1.0
 */
@RegisterInfo( id = 332 )
public class Dysprosium extends BlockAtom implements BlockDysprosium {

    @Override
    public int getBlockId() {
        return 332;
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
        return BlockType.DYSPROSIUM;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public int getProtons() {
        return 66;
    }

    @Override
    public int getElectrons() {
        return 66;
    }

    @Override
    public int getNeutrons() {
        return 90;
    }

    @Override
    public int getAtomicWeight() {
        return 163;
    }
}
