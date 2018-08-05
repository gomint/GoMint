package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockPromethium;
import io.gomint.world.block.BlockType;

/**
 * @author Kaooot
 * @version 1.0
 */
@RegisterInfo( id = 327 )
public class Promethium extends BlockAtom implements BlockPromethium {

    @Override
    public int getBlockId() {
        return 327;
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
        return BlockType.PROMETHIUM;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public int getProtons() {
        return 61;
    }

    @Override
    public int getElectrons() {
        return 61;
    }

    @Override
    public int getNeutrons() {
        return 83;
    }

    @Override
    public int getAtomicWeight() {
        return 145;
    }
}
