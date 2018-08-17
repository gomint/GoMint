package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockType;
import io.gomint.world.block.BlockXenon;

/**
 * @author Kaooot
 * @version 1.0
 */
@RegisterInfo( id = 320 )
public class Xenon extends BlockAtom implements BlockXenon {

    @Override
    public int getBlockId() {
        return 320;
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
        return BlockType.XENON;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public int getProtons() {
        return 54;
    }

    @Override
    public int getElectrons() {
        return 54;
    }

    @Override
    public int getNeutrons() {
        return 70;
    }

    @Override
    public int getAtomicWeight() {
        return 131;
    }
}
