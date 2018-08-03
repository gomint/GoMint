package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockHolmium;
import io.gomint.world.block.BlockType;

/**
 * @author Kaooot
 * @version 1.0
 */
@RegisterInfo( id = 333 )
public class Holmium extends BlockAtom implements BlockHolmium {

    @Override
    public int getBlockId() {
        return 333;
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
        return BlockType.HOLMIUM;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public int getProtons() {
        return 67;
    }

    @Override
    public int getElectrons() {
        return 67;
    }

    @Override
    public int getNeutrons() {
        return 98;
    }

    @Override
    public int getAtomicWeight() {
        return 165;
    }
}
