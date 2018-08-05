package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockRadium;
import io.gomint.world.block.BlockType;

/**
 * @author Kaooot
 * @version 1.0
 */
@RegisterInfo( id = 354 )
public class Radium extends BlockAtom implements BlockRadium {

    @Override
    public int getBlockId() {
        return 354;
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
        return BlockType.RADIUM;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public int getProtons() {
        return 88;
    }

    @Override
    public int getElectrons() {
        return 88;
    }

    @Override
    public int getNeutrons() {
        return 135;
    }

    @Override
    public int getAtomicWeight() {
        return 226;
    }
}
