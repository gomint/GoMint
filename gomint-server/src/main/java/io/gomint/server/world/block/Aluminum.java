package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockAluminum;
import io.gomint.world.block.BlockType;

/**
 * @author Kaooot
 * @version 1.0
 */
@RegisterInfo( id = 279 )
public class Aluminum extends BlockAtom implements BlockAluminum {

    @Override
    public int getBlockId() {
        return 279;
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
        return BlockType.ALUMINUM;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public int getProtons() {
        return 12;
    }

    @Override
    public int getElectrons() {
        return 12;
    }

    @Override
    public int getNeutrons() {
        return 12;
    }

    @Override
    public int getAtomicWeight() {
        return 24;
    }
}
