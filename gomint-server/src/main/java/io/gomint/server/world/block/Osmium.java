package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockOsmium;
import io.gomint.world.block.BlockType;

/**
 * @author Kaooot
 * @version 1.0
 */
@RegisterInfo( id = 342 )
public class Osmium extends BlockAtom implements BlockOsmium {

    @Override
    public int getBlockId() {
        return 342;
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
        return BlockType.OSMIUM;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public int getProtons() {
        return 75;
    }

    @Override
    public int getElectrons() {
        return 75;
    }

    @Override
    public int getNeutrons() {
        return 108;
    }

    @Override
    public int getAtomicWeight() {
        return 190;
    }
}
