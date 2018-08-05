package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockHassium;
import io.gomint.world.block.BlockType;

/**
 * @author Kaooot
 * @version 1.0
 */
@RegisterInfo( id = 374 )
public class Hassium extends BlockAtom implements BlockHassium {

    @Override
    public int getBlockId() {
        return 374;
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
        return BlockType.HASSIUM;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public int getProtons() {
        return 108;
    }

    @Override
    public int getElectrons() {
        return 108;
    }

    @Override
    public int getNeutrons() {
        return 161;
    }

    @Override
    public int getAtomicWeight() {
        return 269;
    }
}
