package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockNihonium;
import io.gomint.world.block.BlockType;

/**
 * @author Kaooot
 * @version 1.0
 */
@RegisterInfo( id = 379 )
public class Nihonium extends BlockAtom implements BlockNihonium {

    @Override
    public int getBlockId() {
        return 379;
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
        return BlockType.NIHONIUM;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public int getProtons() {
        return 113;
    }

    @Override
    public int getElectrons() {
        return 113;
    }

    @Override
    public int getNeutrons() {
        return 173;
    }

    @Override
    public int getAtomicWeight() {
        return 286;
    }
}
