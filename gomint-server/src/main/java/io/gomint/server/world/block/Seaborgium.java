package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockSeaborgium;
import io.gomint.world.block.BlockType;

/**
 * @author Kaooot
 * @version 1.0
 */
@RegisterInfo( id = 372 )
public class Seaborgium extends BlockAtom implements BlockSeaborgium {

    @Override
    public int getBlockId() {
        return 372;
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
        return BlockType.SEABORGIUM;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public int getProtons() {
        return 106;
    }

    @Override
    public int getElectrons() {
        return 106;
    }

    @Override
    public int getNeutrons() {
        return 163;
    }

    @Override
    public int getAtomicWeight() {
        return 269;
    }
}
