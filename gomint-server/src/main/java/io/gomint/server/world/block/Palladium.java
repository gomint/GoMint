package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockPalladium;
import io.gomint.world.block.BlockType;

/**
 * @author Kaooot
 * @version 1.0
 */
@RegisterInfo( id = 312 )
public class Palladium extends BlockAtom implements BlockPalladium {

    @Override
    public int getBlockId() {
        return 312;
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
        return BlockType.PALLADIUM;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public int getProtons() {
        return 46;
    }

    @Override
    public int getElectrons() {
        return 46;
    }

    @Override
    public int getNeutrons() {
        return 56;
    }

    @Override
    public int getAtomicWeight() {
        return 106;
    }
}
