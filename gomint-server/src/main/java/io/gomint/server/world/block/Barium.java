package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockBarium;
import io.gomint.world.block.BlockType;

/**
 * @author Kaooot
 * @version 1.0
 */
@RegisterInfo( id = 322 )
public class Barium extends BlockAtom implements BlockBarium {

    @Override
    public int getBlockId() {
        return 322;
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
        return BlockType.BARIUM;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public int getProtons() {
        return 56;
    }

    @Override
    public int getElectrons() {
        return 56;
    }

    @Override
    public int getNeutrons() {
        return 74;
    }

    @Override
    public int getAtomicWeight() {
        return 137;
    }
}
