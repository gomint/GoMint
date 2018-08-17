package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockBoron;
import io.gomint.world.block.BlockType;

/**
 * @author Kaooot
 * @version 1.0
 */
@RegisterInfo( id = 271 )
public class Boron extends BlockAtom implements BlockBoron {

    @Override
    public int getBlockId() {
        return 271;
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
        return BlockType.BORON;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public int getProtons() {
        return 5;
    }

    @Override
    public int getElectrons() {
        return 5;
    }

    @Override
    public int getNeutrons() {
        return 5;
    }

    @Override
    public int getAtomicWeight() {
        return 11;
    }
}
