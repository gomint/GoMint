package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockCerium;
import io.gomint.world.block.BlockType;

/**
 * @author Kaooot
 * @version 1.0
 */
@RegisterInfo( id = 324 )
public class Cerium extends BlockAtom implements BlockCerium {

    @Override
    public int getBlockId() {
        return 324;
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
        return BlockType.CERIUM;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public int getProtons() {
        return 58;
    }

    @Override
    public int getElectrons() {
        return 58;
    }

    @Override
    public int getNeutrons() {
        return 78;
    }

    @Override
    public int getAtomicWeight() {
        return 140;
    }
}
