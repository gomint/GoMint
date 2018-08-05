package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockLithium;
import io.gomint.world.block.BlockType;

/**
 * @author Kaooot
 * @version 1.0
 */
@RegisterInfo( id = 269 )
public class Lithium extends BlockAtom implements BlockLithium {

    @Override
    public int getBlockId() {
        return 269;
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
        return BlockType.LITHIUM;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public int getProtons() {
        return 3;
    }

    @Override
    public int getElectrons() {
        return 3;
    }

    @Override
    public int getNeutrons() {
        return 3;
    }

    @Override
    public int getAtomicWeight() {
        return 7;
    }
}
