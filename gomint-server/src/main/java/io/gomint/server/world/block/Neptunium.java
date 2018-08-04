package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockNeptunium;
import io.gomint.world.block.BlockType;

/**
 * @author Kaooot
 * @version 1.0
 */
@RegisterInfo( id = 359 )
public class Neptunium extends BlockAtom implements BlockNeptunium {

    @Override
    public int getBlockId() {
        return 359;
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
        return BlockType.NEPTUNIUM;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public int getProtons() {
        return 93;
    }

    @Override
    public int getElectrons() {
        return 93;
    }

    @Override
    public int getNeutrons() {
        return 144;
    }

    @Override
    public int getAtomicWeight() {
        return 237;
    }
}
