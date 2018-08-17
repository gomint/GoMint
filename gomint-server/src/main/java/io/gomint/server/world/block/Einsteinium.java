package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockEinsteinium;
import io.gomint.world.block.BlockType;

/**
 * @author Kaooot
 * @version 1.0
 */
@RegisterInfo( id = 365 )
public class Einsteinium extends BlockAtom implements BlockEinsteinium {

    @Override
    public int getBlockId() {
        return 365;
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
        return BlockType.EINSTEINIUM;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public int getProtons() {
        return 99;
    }

    @Override
    public int getElectrons() {
        return 99;
    }

    @Override
    public int getNeutrons() {
        return 153;
    }

    @Override
    public int getAtomicWeight() {
        return 252;
    }
}
