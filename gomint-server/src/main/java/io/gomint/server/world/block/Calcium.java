package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockCalcium;
import io.gomint.world.block.BlockType;

/**
 * @author Kaooot
 * @version 1.0
 */
@RegisterInfo( id = 286 )
public class Calcium extends BlockAtom implements BlockCalcium {

    @Override
    public int getBlockId() {
        return 286;
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
        return BlockType.CALCIUM;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public int getProtons() {
        return 20;
    }

    @Override
    public int getElectrons() {
        return 20;
    }

    @Override
    public int getNeutrons() {
        return 20;
    }

    @Override
    public int getAtomicWeight() {
        return 40;
    }
}
