package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockType;
import io.gomint.world.block.BlockZirconium;

/**
 * @author Kaooot
 * @version 1.0
 */
@RegisterInfo( id = 306 )
public class Zirconium extends BlockAtom implements BlockZirconium {

    @Override
    public int getBlockId() {
        return 306;
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
        return BlockType.ZIRCONIUM;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public int getProtons() {
        return 40;
    }

    @Override
    public int getElectrons() {
        return 40;
    }

    @Override
    public int getNeutrons() {
        return 50;
    }

    @Override
    public int getAtomicWeight() {
        return 91;
    }
}
