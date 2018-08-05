package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockNiobium;
import io.gomint.world.block.BlockType;

/**
 * @author Kaooot
 * @version 1.0
 */
@RegisterInfo( id = 307 )
public class Niobium extends BlockAtom implements BlockNiobium {

    @Override
    public int getBlockId() {
        return 307;
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
        return BlockType.NIOBIUM;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public int getProtons() {
        return 41;
    }

    @Override
    public int getElectrons() {
        return 41;
    }

    @Override
    public int getNeutrons() {
        return 52;
    }

    @Override
    public int getAtomicWeight() {
        return 93;
    }
}
