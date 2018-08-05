package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockActinium;
import io.gomint.world.block.BlockType;

/**
 * @author Kaooot
 * @version 1.0
 */
@RegisterInfo( id = 355 )
public class Actinium extends BlockAtom implements BlockActinium {

    @Override
    public int getBlockId() {
        return 355;
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
        return BlockType.ACTINIUM;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public int getProtons() {
        return 89;
    }

    @Override
    public int getElectrons() {
        return 89;
    }

    @Override
    public int getNeutrons() {
        return 138;
    }

    @Override
    public int getAtomicWeight() {
        return 227;
    }
}
