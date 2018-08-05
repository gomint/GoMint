package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockMercury;
import io.gomint.world.block.BlockType;

/**
 * @author Kaooot
 * @version 1.0
 */
@RegisterInfo( id = 346 )
public class Mercury extends BlockAtom implements BlockMercury {

    @Override
    public int getBlockId() {
        return 346;
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
        return BlockType.MERCURY;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public int getProtons() {
        return 80;
    }

    @Override
    public int getElectrons() {
        return 80;
    }

    @Override
    public int getNeutrons() {
        return 116;
    }

    @Override
    public int getAtomicWeight() {
        return 201;
    }
}
