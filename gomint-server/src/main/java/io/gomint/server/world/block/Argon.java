package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockArgon;
import io.gomint.world.block.BlockType;

/**
 * @author Kaooot
 * @version 1.0
 */
@RegisterInfo( id = 284 )
public class Argon extends BlockAtom implements BlockArgon {

    @Override
    public int getBlockId() {
        return 284;
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
        return BlockType.ARGON;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public int getProtons() {
        return 18;
    }

    @Override
    public int getElectrons() {
        return 18;
    }

    @Override
    public int getNeutrons() {
        return 18;
    }

    @Override
    public int getAtomicWeight() {
        return 40;
    }
}
