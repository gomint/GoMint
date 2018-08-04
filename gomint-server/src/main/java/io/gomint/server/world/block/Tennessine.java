package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockTennessine;
import io.gomint.world.block.BlockType;

/**
 * @author Kaooot
 * @version 1.0
 */
@RegisterInfo( id = 383 )
public class Tennessine extends BlockAtom implements BlockTennessine {

    @Override
    public int getBlockId() {
        return 383;
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
        return BlockType.TENNESSINE;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public int getProtons() {
        return 117;
    }

    @Override
    public int getElectrons() {
        return 117;
    }

    @Override
    public int getNeutrons() {
        return 177;
    }

    @Override
    public int getAtomicWeight() {
        return 294;
    }
}
