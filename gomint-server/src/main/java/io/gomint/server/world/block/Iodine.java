package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockIodine;
import io.gomint.world.block.BlockType;

/**
 * @author Kaooot
 * @version 1.0
 */
@RegisterInfo( id = 319 )
public class Iodine extends BlockAtom implements BlockIodine {

    @Override
    public int getBlockId() {
        return 319;
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
        return BlockType.IODINE;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public int getProtons() {
        return 53;
    }

    @Override
    public int getElectrons() {
        return 53;
    }

    @Override
    public int getNeutrons() {
        return 70;
    }

    @Override
    public int getAtomicWeight() {
        return 127;
    }
}
