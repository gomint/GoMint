package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockMolybdenum;
import io.gomint.world.block.BlockType;

/**
 * @author Kaooot
 * @version 1.0
 */
@RegisterInfo( id = 308 )
public class Molybdenum extends BlockAtom implements BlockMolybdenum {

    @Override
    public int getBlockId() {
        return 308;
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
        return BlockType.MOLYBDENUM;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public int getProtons() {
        return 42;
    }

    @Override
    public int getElectrons() {
        return 42;
    }

    @Override
    public int getNeutrons() {
        return 50;
    }

    @Override
    public int getAtomicWeight() {
        return 96;
    }
}
