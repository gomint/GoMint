package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockCadmium;
import io.gomint.world.block.BlockType;

/**
 * @author Kaooot
 * @version 1.0
 */
@RegisterInfo( id = 314 )
public class Cadmium extends BlockAtom implements BlockCadmium {

    @Override
    public int getBlockId() {
        return 314;
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
        return BlockType.CADMIUM;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public int getProtons() {
        return 48;
    }

    @Override
    public int getElectrons() {
        return 48;
    }

    @Override
    public int getNeutrons() {
        return 58;
    }

    @Override
    public int getAtomicWeight() {
        return 112;
    }
}
