package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockFermium;
import io.gomint.world.block.BlockType;

/**
 * @author Kaooot
 * @version 1.0
 */
@RegisterInfo( id = 366 )
public class Fermium extends BlockAtom implements BlockFermium {

    @Override
    public int getBlockId() {
        return 366;
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
        return BlockType.FERMIUM;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public int getProtons() {
        return 100;
    }

    @Override
    public int getElectrons() {
        return 100;
    }

    @Override
    public int getNeutrons() {
        return 157;
    }

    @Override
    public int getAtomicWeight() {
        return 257;
    }
}
