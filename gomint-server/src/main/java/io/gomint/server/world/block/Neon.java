package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockNeon;
import io.gomint.world.block.BlockType;

/**
 * @author Kaooot
 * @version 1.0
 */
@RegisterInfo( id = 276 )
public class Neon extends BlockAtom implements BlockNeon {

    @Override
    public int getBlockId() {
        return 276;
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
        return BlockType.NEON;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public int getProtons() {
        return 10;
    }

    @Override
    public int getElectrons() {
        return 10;
    }

    @Override
    public int getNeutrons() {
        return 10;
    }

    @Override
    public int getAtomicWeight() {
        return 20;
    }
}
