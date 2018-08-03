package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockGermanium;
import io.gomint.world.block.BlockType;

/**
 * @author Kaooot
 * @version 1.0
 */
@RegisterInfo( id = 298 )
public class Germanium extends BlockAtom implements BlockGermanium {

    @Override
    public int getBlockId() {
        return 298;
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
        return BlockType.GERMANIUM;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public int getProtons() {
        return 32;
    }

    @Override
    public int getElectrons() {
        return 32;
    }

    @Override
    public int getNeutrons() {
        return 36;
    }

    @Override
    public int getAtomicWeight() {
        return 73;
    }
}
