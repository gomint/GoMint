package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockRhenium;
import io.gomint.world.block.BlockType;

/**
 * @author Kaooot
 * @version 1.0
 */
@RegisterInfo( id = 341 )
public class Rhenium extends BlockAtom implements BlockRhenium {

    @Override
    public int getBlockId() {
        return 341;
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
        return BlockType.RHENIUM;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public int getProtons() {
        return 75;
    }

    @Override
    public int getElectrons() {
        return 75;
    }

    @Override
    public int getNeutrons() {
        return 110;
    }

    @Override
    public int getAtomicWeight() {
        return 186;
    }
}
