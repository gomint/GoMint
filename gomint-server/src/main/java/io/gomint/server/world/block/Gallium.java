package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockGallium;
import io.gomint.world.block.BlockType;

/**
 * @author Kaooot
 * @version 1.0
 */
@RegisterInfo( id = 297 )
public class Gallium extends BlockAtom implements BlockGallium {

    @Override
    public int getBlockId() {
        return 297;
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
        return BlockType.GALLIUM;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public int getProtons() {
        return 31;
    }

    @Override
    public int getElectrons() {
        return 31;
    }

    @Override
    public int getNeutrons() {
        return 36;
    }

    @Override
    public int getAtomicWeight() {
        return 70;
    }
}
