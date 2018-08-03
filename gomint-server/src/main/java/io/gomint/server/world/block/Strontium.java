package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockStrontium;
import io.gomint.world.block.BlockType;

/**
 * @author Kaooot
 * @version 1.0
 */
@RegisterInfo( id = 304 )
public class Strontium extends BlockAtom implements BlockStrontium {

    @Override
    public int getBlockId() {
        return 304;
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
        return BlockType.STRONTIUM;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public int getProtons() {
        return 38;
    }

    @Override
    public int getElectrons() {
        return 38;
    }

    @Override
    public int getNeutrons() {
        return 46;
    }

    @Override
    public int getAtomicWeight() {
        return 88;
    }
}
