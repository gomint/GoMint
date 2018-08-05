package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockRadon;
import io.gomint.world.block.BlockType;

/**
 * @author Kaooot
 * @version 1.0
 */
@RegisterInfo( id = 352 )
public class Radon extends BlockAtom implements BlockRadon {

    @Override
    public int getBlockId() {
        return 352;
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
        return BlockType.RADON;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public int getProtons() {
        return 86;
    }

    @Override
    public int getElectrons() {
        return 86;
    }

    @Override
    public int getNeutrons() {
        return 125;
    }

    @Override
    public int getAtomicWeight() {
        return 222;
    }
}
