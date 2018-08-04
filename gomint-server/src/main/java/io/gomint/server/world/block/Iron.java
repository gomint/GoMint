package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockIron;
import io.gomint.world.block.BlockType;

/**
 * @author Kaooot
 * @version 1.0
 */
@RegisterInfo( id = 292 )
public class Iron extends BlockAtom implements BlockIron {

    @Override
    public int getBlockId() {
        return 292;
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
        return BlockType.IRON;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public int getProtons() {
        return 26;
    }

    @Override
    public int getElectrons() {
        return 26;
    }

    @Override
    public int getNeutrons() {
        return 26;
    }

    @Override
    public int getAtomicWeight() {
        return 56;
    }
}
