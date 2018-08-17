package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockLanthanum;
import io.gomint.world.block.BlockType;

/**
 * @author Kaooot
 * @version 1.0
 */
@RegisterInfo( id = 323 )
public class Lanthanum extends BlockAtom implements BlockLanthanum {

    @Override
    public int getBlockId() {
        return 323;
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
        return BlockType.LANTHANUM;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public int getProtons() {
        return 57;
    }

    @Override
    public int getElectrons() {
        return 57;
    }

    @Override
    public int getNeutrons() {
        return 81;
    }

    @Override
    public int getAtomicWeight() {
        return 139;
    }
}
