package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockTin;
import io.gomint.world.block.BlockType;

/**
 * @author Kaooot
 * @version 1.0
 */
@RegisterInfo( id = 316 )
public class Tin extends BlockAtom implements BlockTin {

    @Override
    public int getBlockId() {
        return 316;
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
        return BlockType.TIN;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public int getProtons() {
        return 50;
    }

    @Override
    public int getElectrons() {
        return 50;
    }

    @Override
    public int getNeutrons() {
        return 62;
    }

    @Override
    public int getAtomicWeight() {
        return 119;
    }
}
