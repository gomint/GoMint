package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockAstatine;
import io.gomint.world.block.BlockType;

/**
 * @author Kaooot
 * @version 1.0
 */
@RegisterInfo( id = 351 )
public class Astatine extends BlockAtom implements BlockAstatine {

    @Override
    public int getBlockId() {
        return 351;
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
        return BlockType.ASTATINE;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public int getProtons() {
        return 85;
    }

    @Override
    public int getElectrons() {
        return 85;
    }

    @Override
    public int getNeutrons() {
        return 125;
    }

    @Override
    public int getAtomicWeight() {
        return 210;
    }
}
