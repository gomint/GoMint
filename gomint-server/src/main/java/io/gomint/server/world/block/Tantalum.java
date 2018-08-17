package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockTantalum;
import io.gomint.world.block.BlockType;

/**
 * @author Kaooot
 * @version 1.0
 */
@RegisterInfo( id = 339 )
public class Tantalum extends BlockAtom implements BlockTantalum {

    @Override
    public int getBlockId() {
        return 339;
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
        return BlockType.TANTALUM;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public int getProtons() {
        return 73;
    }

    @Override
    public int getElectrons() {
        return 73;
    }

    @Override
    public int getNeutrons() {
        return 107;
    }

    @Override
    public int getAtomicWeight() {
        return 181;
    }
}
