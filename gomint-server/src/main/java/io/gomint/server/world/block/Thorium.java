package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockThorium;
import io.gomint.world.block.BlockType;


/**
 * @author Kaooot
 * @version 1.0
 */
@RegisterInfo( id = 356 )
public class Thorium extends BlockAtom implements BlockThorium {

    @Override
    public int getBlockId() {
        return 355;
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
        return BlockType.THORIUM;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public int getProtons() {
        return 90;
    }

    @Override
    public int getElectrons() {
        return 90;
    }

    @Override
    public int getNeutrons() {
        return 138;
    }

    @Override
    public int getAtomicWeight() {
        return 232;
    }
}
