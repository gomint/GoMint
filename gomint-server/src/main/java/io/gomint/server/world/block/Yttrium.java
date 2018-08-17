package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockType;
import io.gomint.world.block.BlockYttrium;

/**
 * @author Kaooot
 * @version 1.0
 */
@RegisterInfo( id = 305 )
public class Yttrium extends BlockAtom implements BlockYttrium {

    @Override
    public int getBlockId() {
        return 305;
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
        return BlockType.YTTRIUM;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public int getProtons() {
        return 39;
    }

    @Override
    public int getElectrons() {
        return 39;
    }

    @Override
    public int getNeutrons() {
        return 50;
    }

    @Override
    public int getAtomicWeight() {
        return 89;
    }
}
