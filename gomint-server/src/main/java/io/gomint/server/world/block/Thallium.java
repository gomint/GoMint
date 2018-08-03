package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockThallium;
import io.gomint.world.block.BlockType;

/**
 * @author Kaooot
 * @version 1.0
 */
@RegisterInfo( id = 347 )
public class Thallium extends BlockAtom implements BlockThallium {

    @Override
    public int getBlockId() {
        return 347;
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
        return BlockType.THALLIUM;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public int getProtons() {
        return 81;
    }

    @Override
    public int getElectrons() {
        return 81;
    }

    @Override
    public int getNeutrons() {
        return 120;
    }

    @Override
    public int getAtomicWeight() {
        return 204;
    }
}
