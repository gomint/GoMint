package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockMoscovium;
import io.gomint.world.block.BlockType;

/**
 * @author Kaooot
 * @version 1.0
 */
@RegisterInfo( id = 381 )
public class Moscovium extends BlockAtom implements BlockMoscovium {

    @Override
    public int getBlockId() {
        return 381;
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
        return BlockType.MOSCOVIUM;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public int getProtons() {
        return 115;
    }

    @Override
    public int getElectrons() {
        return 115;
    }

    @Override
    public int getNeutrons() {
        return 175;
    }

    @Override
    public int getAtomicWeight() {
        return 290;
    }
}
