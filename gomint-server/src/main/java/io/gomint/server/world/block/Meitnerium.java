package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockMeitnerium;
import io.gomint.world.block.BlockType;

/**
 * @author Kaooot
 * @version 1.0
 */
@RegisterInfo( id = 375 )
public class Meitnerium extends BlockAtom implements BlockMeitnerium {

    @Override
    public int getBlockId() {
        return 375;
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
        return BlockType.MEITNERIUM;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public int getProtons() {
        return 109;
    }

    @Override
    public int getElectrons() {
        return 109;
    }

    @Override
    public int getNeutrons() {
        return 169;
    }

    @Override
    public int getAtomicWeight() {
        return 278;
    }
}
