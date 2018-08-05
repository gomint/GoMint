package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockGold;
import io.gomint.world.block.BlockType;

/**
 * @author Kaooot
 * @version 1.0
 */
@RegisterInfo( id = 345 )
public class Gold extends BlockAtom implements BlockGold {

    @Override
    public int getBlockId() {
        return 345;
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
        return BlockType.GOLD;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public int getProtons() {
        return 79;
    }

    @Override
    public int getElectrons() {
        return 79;
    }

    @Override
    public int getNeutrons() {
        return 118;
    }

    @Override
    public int getAtomicWeight() {
        return 197;
    }
}
