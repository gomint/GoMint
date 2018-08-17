package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockTechnetium;
import io.gomint.world.block.BlockType;

/**
 * @author Kaooot
 * @version 1.0
 */
@RegisterInfo( id = 309 )
public class Technetium extends BlockAtom implements BlockTechnetium {

    @Override
    public int getBlockId() {
        return 309;
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
        return BlockType.TECHNETIUM;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public int getProtons() {
        return 43;
    }

    @Override
    public int getElectrons() {
        return 43;
    }

    @Override
    public int getNeutrons() {
        return 54;
    }

    @Override
    public int getAtomicWeight() {
        return 98;
    }
}
