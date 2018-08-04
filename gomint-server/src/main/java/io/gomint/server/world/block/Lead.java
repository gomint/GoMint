package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockLead;
import io.gomint.world.block.BlockType;

/**
 * @author Kaooot
 * @version 1.0
 */
@RegisterInfo( id = 348 )
public class Lead extends BlockAtom implements BlockLead {

    @Override
    public int getBlockId() {
        return 348;
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
        return BlockType.LEAD;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public int getProtons() {
        return 82;
    }

    @Override
    public int getElectrons() {
        return 82;
    }

    @Override
    public int getNeutrons() {
        return 122;
    }

    @Override
    public int getAtomicWeight() {
        return 207;
    }
}
