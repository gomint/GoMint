package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockErbium;
import io.gomint.world.block.BlockType;

/**
 * @author Kaooot
 * @version 1.0
 */
@RegisterInfo( id = 334 )
public class Erbium extends BlockAtom implements BlockErbium {

    @Override
    public int getBlockId() {
        return 334;
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
        return BlockType.ERBIUM;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public int getProtons() {
        return 68;
    }

    @Override
    public int getElectrons() {
        return 68;
    }

    @Override
    public int getNeutrons() {
        return 94;
    }

    @Override
    public int getAtomicWeight() {
        return 167;
    }
}
