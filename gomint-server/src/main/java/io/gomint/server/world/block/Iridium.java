package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockIridium;
import io.gomint.world.block.BlockType;

/**
 * @author Kaooot
 * @version 1.0
 */
@RegisterInfo( id = 343 )
public class Iridium extends BlockAtom implements BlockIridium {

    @Override
    public int getBlockId() {
        return 343;
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
        return BlockType.IRIDIUM;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public int getProtons() {
        return 77;
    }

    @Override
    public int getElectrons() {
        return 77;
    }

    @Override
    public int getNeutrons() {
        return 114;
    }

    @Override
    public int getAtomicWeight() {
        return 192;
    }
}
