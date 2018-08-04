package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockLawrencium;
import io.gomint.world.block.BlockType;

/**
 * @author Kaooot
 * @version 1.0
 */
@RegisterInfo( id = 369 )
public class Lawrencium extends BlockAtom implements BlockLawrencium {

    @Override
    public int getBlockId() {
        return 369;
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
        return BlockType.LAWRENCIUM;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public int getProtons() {
        return 103;
    }

    @Override
    public int getElectrons() {
        return 103;
    }

    @Override
    public int getNeutrons() {
        return 159;
    }

    @Override
    public int getAtomicWeight() {
        return 262;
    }
}
