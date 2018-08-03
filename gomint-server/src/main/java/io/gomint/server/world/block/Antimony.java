package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockAntimony;
import io.gomint.world.block.BlockType;

/**
 * @author Kaooot
 * @version 1.0
 */
@RegisterInfo( id = 317 )
public class Antimony extends BlockAtom implements BlockAntimony {

    @Override
    public int getBlockId() {
        return 317;
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
        return BlockType.ANTIMONY;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public int getProtons() {
        return 51;
    }

    @Override
    public int getElectrons() {
        return 51;
    }

    @Override
    public int getNeutrons() {
        return 70;
    }

    @Override
    public int getAtomicWeight() {
        return 122;
    }
}
