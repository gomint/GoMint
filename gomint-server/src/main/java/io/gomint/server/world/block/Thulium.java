package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockThulium;
import io.gomint.world.block.BlockType;

/**
 * @author Kaooot
 * @version 1.0
 */
@RegisterInfo( id = 335 )
public class Thulium extends BlockAtom implements BlockThulium {

    @Override
    public int getBlockId() {
        return 335;
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
        return BlockType.THULIUM;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public int getProtons() {
        return 69;
    }

    @Override
    public int getElectrons() {
        return 69;
    }

    @Override
    public int getNeutrons() {
        return 100;
    }

    @Override
    public int getAtomicWeight() {
        return 169;
    }
}
