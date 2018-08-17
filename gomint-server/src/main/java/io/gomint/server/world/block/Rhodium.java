package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockRhodium;
import io.gomint.world.block.BlockType;

/**
 * @author Kaooot
 * @version 1.0
 */
@RegisterInfo( id = 311 )
public class Rhodium extends BlockAtom implements BlockRhodium {

    @Override
    public int getBlockId() {
        return 311;
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
        return BlockType.RHODIUM;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public int getProtons() {
        return 45;
    }

    @Override
    public int getElectrons() {
        return 45;
    }

    @Override
    public int getNeutrons() {
        return 58;
    }

    @Override
    public int getAtomicWeight() {
        return 103;
    }
}
