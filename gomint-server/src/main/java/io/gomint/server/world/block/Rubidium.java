package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockRubidium;
import io.gomint.world.block.BlockType;

/**
 * @author Kaooot
 * @version 1.0
 */
@RegisterInfo( id = 303 )
public class Rubidium extends BlockAtom implements BlockRubidium {

    @Override
    public int getBlockId() {
        return 303;
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
        return BlockType.RUBIDIUM;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public int getProtons() {
        return 37;
    }

    @Override
    public int getElectrons() {
        return 37;
    }

    @Override
    public int getNeutrons() {
        return 48;
    }

    @Override
    public int getAtomicWeight() {
        return 85;
    }
}
