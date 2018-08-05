package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockNobelium;
import io.gomint.world.block.BlockType;

/**
 * @author Kaooot
 * @version 1.0
 */
@RegisterInfo( id = 368 )
public class Nobelium extends BlockAtom implements BlockNobelium {

    @Override
    public int getBlockId() {
        return 368;
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
        return BlockType.NOBELIUM;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public int getProtons() {
        return 102;
    }

    @Override
    public int getElectrons() {
        return 102;
    }

    @Override
    public int getNeutrons() {
        return 157;
    }

    @Override
    public int getAtomicWeight() {
        return 259;
    }
}
