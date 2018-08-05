package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockType;
import io.gomint.world.block.BlockYtterbium;

/**
 * @author Kaooot
 * @version 1.0
 */
@RegisterInfo( id = 336 )
public class Ytterbium extends BlockAtom implements BlockYtterbium {

    @Override
    public int getBlockId() {
        return 336;
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
        return BlockType.YTTERBIUM;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public int getProtons() {
        return 70;
    }

    @Override
    public int getElectrons() {
        return 70;
    }

    @Override
    public int getNeutrons() {
        return 98;
    }

    @Override
    public int getAtomicWeight() {
        return 173;
    }
}
