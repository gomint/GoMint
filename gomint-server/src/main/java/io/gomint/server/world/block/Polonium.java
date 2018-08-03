package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockPolonium;
import io.gomint.world.block.BlockType;

/**
 * @author Kaooot
 * @version 1.0
 */
@RegisterInfo( id = 350 )
public class Polonium extends BlockAtom implements BlockPolonium {

    @Override
    public int getBlockId() {
        return 350;
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
        return BlockType.POLONIUM;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public int getProtons() {
        return 84;
    }

    @Override
    public int getElectrons() {
        return 84;
    }

    @Override
    public int getNeutrons() {
        return 125;
    }

    @Override
    public int getAtomicWeight() {
        return 209;
    }
}
