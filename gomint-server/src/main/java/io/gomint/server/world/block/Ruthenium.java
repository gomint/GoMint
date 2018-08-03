package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockRuthenium;
import io.gomint.world.block.BlockType;

/**
 * @author Kaooot
 * @version 1.0
 */
@RegisterInfo( id = 310 )
public class Ruthenium extends BlockAtom implements BlockRuthenium {

    @Override
    public int getBlockId() {
        return 310;
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
        return BlockType.RUTHENIUM;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public int getProtons() {
        return 44;
    }

    @Override
    public int getElectrons() {
        return 44;
    }

    @Override
    public int getNeutrons() {
        return 52;
    }

    @Override
    public int getAtomicWeight() {
        return 101;
    }
}
