package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockType;
import io.gomint.world.block.BlockZinc;

/**
 * @author Kaooot
 * @version 1.0
 */
@RegisterInfo( id = 296 )
public class Zinc extends BlockAtom implements BlockZinc {

    @Override
    public int getBlockId() {
        return 296;
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
        return BlockType.ZINC;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public int getProtons() {
        return 29;
    }

    @Override
    public int getElectrons() {
        return 29;
    }

    @Override
    public int getNeutrons() {
        return 34;
    }

    @Override
    public int getAtomicWeight() {
        return 65;
    }
}
