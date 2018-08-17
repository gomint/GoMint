package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockType;
import io.gomint.world.block.BlockVanadium;

/**
 * @author Kaooot
 * @version 1.0
 */
@RegisterInfo( id = 289 )
public class Vanadium extends BlockAtom implements BlockVanadium {

    @Override
    public int getBlockId() {
        return 289;
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
        return BlockType.VANADIUM;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public int getProtons() {
        return 23;
    }

    @Override
    public int getElectrons() {
        return 23;
    }

    @Override
    public int getNeutrons() {
        return 27;
    }

    @Override
    public int getAtomicWeight() {
        return 51;
    }
}
