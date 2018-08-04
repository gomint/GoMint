package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockPlutonium;
import io.gomint.world.block.BlockType;

/**
 * @author Kaooot
 * @version 1.0
 */
@RegisterInfo( id = 360 )
public class Plutonium extends BlockAtom implements BlockPlutonium {

    @Override
    public int getBlockId() {
        return 360;
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
        return BlockType.PLUTONIUM;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public int getProtons() {
        return 94;
    }

    @Override
    public int getElectrons() {
        return 94;
    }

    @Override
    public int getNeutrons() {
        return 144;
    }

    @Override
    public int getAtomicWeight() {
        return 244;
    }
}
