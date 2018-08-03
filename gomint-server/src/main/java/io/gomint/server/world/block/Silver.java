package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockSilver;
import io.gomint.world.block.BlockType;

/**
 * @author Kaooot
 * @version 1.0
 */
@RegisterInfo( id = 313 )
public class Silver extends BlockAtom implements BlockSilver {

    @Override
    public int getBlockId() {
        return 313;
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
        return BlockType.SILVER;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public int getProtons() {
        return 47;
    }

    @Override
    public int getElectrons() {
        return 47;
    }

    @Override
    public int getNeutrons() {
        return 60;
    }

    @Override
    public int getAtomicWeight() {
        return 108;
    }
}
