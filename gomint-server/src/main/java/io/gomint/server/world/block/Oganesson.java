package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockOganesson;
import io.gomint.world.block.BlockType;

/**
 * @author Kaooot
 * @version 1.0
 */
@RegisterInfo( id = 384 )
public class Oganesson extends BlockAtom implements BlockOganesson {

    @Override
    public int getBlockId() {
        return 384;
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
        return BlockType.OGANESSON;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public int getProtons() {
        return 118;
    }

    @Override
    public int getElectrons() {
        return 118;
    }

    @Override
    public int getNeutrons() {
        return 176;
    }

    @Override
    public int getAtomicWeight() {
        return 294;
    }
}
