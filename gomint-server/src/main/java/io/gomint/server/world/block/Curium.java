package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockCurium;
import io.gomint.world.block.BlockType;

/**
 * @author Kaooot
 * @version 1.0
 */
@RegisterInfo( id = 362 )
public class Curium extends BlockAtom implements BlockCurium {

    @Override
    public int getBlockId() {
        return 362;
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
        return BlockType.CURIUM;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public int getProtons() {
        return 96;
    }

    @Override
    public int getElectrons() {
        return 96;
    }

    @Override
    public int getNeutrons() {
        return 146;
    }

    @Override
    public int getAtomicWeight() {
        return 247;
    }
}
