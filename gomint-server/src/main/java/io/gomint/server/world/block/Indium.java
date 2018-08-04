package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockIndium;
import io.gomint.world.block.BlockType;

/**
 * @author Kaooot
 * @version 1.0
 */
@RegisterInfo( id = 315 )
public class Indium extends BlockAtom implements BlockIndium {

    @Override
    public int getBlockId() {
        return 315;
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
        return BlockType.INDIUM;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public int getProtons() {
        return 49;
    }

    @Override
    public int getElectrons() {
        return 49;
    }

    @Override
    public int getNeutrons() {
        return 62;
    }

    @Override
    public int getAtomicWeight() {
        return 115;
    }
}
