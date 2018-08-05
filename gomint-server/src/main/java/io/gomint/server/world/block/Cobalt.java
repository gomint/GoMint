package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockCobalt;
import io.gomint.world.block.BlockType;

/**
 * @author Kaooot
 * @version 1.0
 */
@RegisterInfo( id = 293 )
public class Cobalt extends BlockAtom implements BlockCobalt {

    @Override
    public int getBlockId() {
        return 293;
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
        return BlockType.COBALT;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public int getProtons() {
        return 27;
    }

    @Override
    public int getElectrons() {
        return 27;
    }

    @Override
    public int getNeutrons() {
        return 30;
    }

    @Override
    public int getAtomicWeight() {
        return 59;
    }
}
