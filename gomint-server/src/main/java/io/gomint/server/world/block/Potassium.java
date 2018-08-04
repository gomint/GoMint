package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockPotassium;
import io.gomint.world.block.BlockType;

/**
 * @author Kaooot
 * @version 1.0
 */
@RegisterInfo( id = 285 )
public class Potassium extends BlockAtom implements BlockPotassium {

    @Override
    public int getBlockId() {
        return 285;
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
        return BlockType.POTASSIUM;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public int getProtons() {
        return 19;
    }

    @Override
    public int getElectrons() {
        return 19;
    }

    @Override
    public int getNeutrons() {
        return 20;
    }

    @Override
    public int getAtomicWeight() {
        return 39;
    }
}
