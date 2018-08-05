package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockHafnium;
import io.gomint.world.block.BlockType;

/**
 * @author Kaooot
 * @version 1.0
 */
@RegisterInfo( id = 338 )
public class Hafnium extends BlockAtom implements BlockHafnium {

    @Override
    public int getBlockId() {
        return 338;
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
        return BlockType.HAFNIUM;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public int getProtons() {
        return 72;
    }

    @Override
    public int getElectrons() {
        return 72;
    }

    @Override
    public int getNeutrons() {
        return 102;
    }

    @Override
    public int getAtomicWeight() {
        return 178;
    }
}
