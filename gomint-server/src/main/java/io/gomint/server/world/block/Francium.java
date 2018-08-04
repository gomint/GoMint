package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockFrancium;
import io.gomint.world.block.BlockType;

/**
 * @author Kaooot
 * @version 1.0
 */
@RegisterInfo( id = 353 )
public class Francium extends BlockAtom implements BlockFrancium {

    @Override
    public int getBlockId() {
        return 353;
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
        return BlockType.FRANCIUM;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public int getProtons() {
        return 87;
    }

    @Override
    public int getElectrons() {
        return 87;
    }

    @Override
    public int getNeutrons() {
        return 136;
    }

    @Override
    public int getAtomicWeight() {
        return 223;
    }
}
