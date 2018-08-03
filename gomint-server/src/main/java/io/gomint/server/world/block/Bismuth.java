package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockBismuth;
import io.gomint.world.block.BlockType;

/**
 * @author Kaooot
 * @version 1.0
 */
@RegisterInfo( id = 349 )
public class Bismuth extends BlockAtom implements BlockBismuth {

    @Override
    public int getBlockId() {
        return 349;
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
        return BlockType.BISMUTH;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public int getProtons() {
        return 83;
    }

    @Override
    public int getElectrons() {
        return 83;
    }

    @Override
    public int getNeutrons() {
        return 124;
    }

    @Override
    public int getAtomicWeight() {
        return 209;
    }
}
