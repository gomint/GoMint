package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockProtactinium;
import io.gomint.world.block.BlockType;

/**
 * @author Kaooot
 * @version 1.0
 */
@RegisterInfo( id = 357 )
public class Protactinium extends BlockAtom implements BlockProtactinium {

    @Override
    public int getBlockId() {
        return 357;
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
        return BlockType.PROTACTINIUM;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public int getProtons() {
        return 91;
    }

    @Override
    public int getElectrons() {
        return 91;
    }

    @Override
    public int getNeutrons() {
        return 140;
    }

    @Override
    public int getAtomicWeight() {
        return 231;
    }
}
