package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockFlerovium;
import io.gomint.world.block.BlockType;

/**
 * @author Kaooot
 * @version 1.0
 */
@RegisterInfo( id = 380 )
public class Flerovium extends BlockAtom implements BlockFlerovium {

    @Override
    public int getBlockId() {
        return 380;
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
        return BlockType.FLEROVIUM;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public int getProtons() {
        return 114;
    }

    @Override
    public int getElectrons() {
        return 114;
    }

    @Override
    public int getNeutrons() {
        return 175;
    }

    @Override
    public int getAtomicWeight() {
        return 289;
    }
}
