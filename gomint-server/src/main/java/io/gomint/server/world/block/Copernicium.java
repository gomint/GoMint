package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockCopernicium;
import io.gomint.world.block.BlockType;

/**
 * @author Kaooot
 * @version 1.0
 */
@RegisterInfo( id = 378 )
public class Copernicium extends BlockAtom implements BlockCopernicium {

    @Override
    public int getBlockId() {
        return 378;
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
        return BlockType.COPERNICIUM;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public int getProtons() {
        return 112;
    }

    @Override
    public int getElectrons() {
        return 112;
    }

    @Override
    public int getNeutrons() {
        return 173;
    }

    @Override
    public int getAtomicWeight() {
        return 285;
    }
}
