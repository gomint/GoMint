package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockArsenic;
import io.gomint.world.block.BlockType;

/**
 * @author Kaooot
 * @version 1.0
 */
@RegisterInfo( id = 299 )
public class Arsenic extends BlockAtom implements BlockArsenic {

    @Override
    public int getBlockId() {
        return 299;
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
        return BlockType.ARSENIC;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public int getProtons() {
        return 33;
    }

    @Override
    public int getElectrons() {
        return 33;
    }

    @Override
    public int getNeutrons() {
        return 42;
    }

    @Override
    public int getAtomicWeight() {
        return 75;
    }
}
