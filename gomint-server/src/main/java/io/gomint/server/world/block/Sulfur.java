package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockSulfur;
import io.gomint.world.block.BlockType;

/**
 * @author Kaooot
 * @version 1.0
 */
@RegisterInfo( id = 282 )
public class Sulfur extends BlockAtom implements BlockSulfur {

    @Override
    public int getBlockId() {
        return 282;
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
        return BlockType.SULFUR;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public int getProtons() {
        return 16;
    }

    @Override
    public int getElectrons() {
        return 16;
    }

    @Override
    public int getNeutrons() {
        return 16;
    }

    @Override
    public int getAtomicWeight() {
        return 32;
    }
}
