package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockTellurium;
import io.gomint.world.block.BlockType;

/**
 * @author Kaooot
 * @version 1.0
 */
@RegisterInfo( id = 318 )
public class Tellurium extends BlockAtom implements BlockTellurium {

    @Override
    public int getBlockId() {
        return 318;
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
        return BlockType.TELLURIUM;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public int getProtons() {
        return 52;
    }

    @Override
    public int getElectrons() {
        return 52;
    }

    @Override
    public int getNeutrons() {
        return 58;
    }

    @Override
    public int getAtomicWeight() {
        return 128;
    }
}
