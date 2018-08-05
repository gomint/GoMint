package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockPraseodymium;
import io.gomint.world.block.BlockType;

/**
 * @author Kaooot
 * @version 1.0
 */
@RegisterInfo( id = 325 )
public class Praseodymium extends BlockAtom implements BlockPraseodymium {

    @Override
    public int getBlockId() {
        return 325;
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
        return BlockType.PRASEODYMIUM;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public int getProtons() {
        return 59;
    }

    @Override
    public int getElectrons() {
        return 59;
    }

    @Override
    public int getNeutrons() {
        return 82;
    }

    @Override
    public int getAtomicWeight() {
        return 141;
    }
}
