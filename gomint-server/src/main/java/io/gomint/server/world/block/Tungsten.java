package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockTungsten;
import io.gomint.world.block.BlockType;

/**
 * @author Kaooot
 * @version 1.0
 */
@RegisterInfo( id = 340 )
public class Tungsten extends BlockAtom implements BlockTungsten {

    @Override
    public int getBlockId() {
        return 340;
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
        return BlockType.TUNGSTEN;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public int getProtons() {
        return 74;
    }

    @Override
    public int getElectrons() {
        return 74;
    }

    @Override
    public int getNeutrons() {
        return 106;
    }

    @Override
    public int getAtomicWeight() {
        return 184;
    }
}
