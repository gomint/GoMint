package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockAmericium;
import io.gomint.world.block.BlockType;

/**
 * @author Kaooot
 * @version 1.0
 */
@RegisterInfo( id = 361 )
public class Americium extends BlockAtom implements BlockAmericium {

    @Override
    public int getBlockId() {
        return 361;
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
        return BlockType.AMERICIUM;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public int getProtons() {
        return 95;
    }

    @Override
    public int getElectrons() {
        return 95;
    }

    @Override
    public int getNeutrons() {
        return 146;
    }

    @Override
    public int getAtomicWeight() {
        return 243;
    }
}
