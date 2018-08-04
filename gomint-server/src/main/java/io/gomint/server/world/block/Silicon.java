package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockSilicon;
import io.gomint.world.block.BlockType;

/**
 * @author Kaooot
 * @version 1.0
 */
@RegisterInfo( id = 280 )
public class Silicon extends BlockAtom implements BlockSilicon {

    @Override
    public int getBlockId() {
        return 280;
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
        return BlockType.SILICON;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public int getProtons() {
        return 14;
    }

    @Override
    public int getElectrons() {
        return 14;
    }

    @Override
    public int getNeutrons() {
        return 14;
    }

    @Override
    public int getAtomicWeight() {
        return 28;
    }
}
