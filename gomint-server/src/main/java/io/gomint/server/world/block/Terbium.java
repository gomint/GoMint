package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockTerbium;
import io.gomint.world.block.BlockType;

/**
 * @author Kaooot
 * @version 1.0
 */
@RegisterInfo( id = 331 )
public class Terbium extends BlockAtom implements BlockTerbium {

    @Override
    public int getBlockId() {
        return 331;
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
        return BlockType.TERBIUM;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public int getProtons() {
        return 65;
    }

    @Override
    public int getElectrons() {
        return 65;
    }

    @Override
    public int getNeutrons() {
        return 94;
    }

    @Override
    public int getAtomicWeight() {
        return 159;
    }
}
