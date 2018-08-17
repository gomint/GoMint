package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockRoentgenium;
import io.gomint.world.block.BlockType;

/**
 * @author Kaooot
 * @version 1.0
 */
@RegisterInfo( id = 377 )
public class Roentgenium extends BlockAtom implements BlockRoentgenium {

    @Override
    public int getBlockId() {
        return 377;
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
        return BlockType.ROENTGENIUM;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public int getProtons() {
        return 111;
    }

    @Override
    public int getElectrons() {
        return 111;
    }

    @Override
    public int getNeutrons() {
        return 171;
    }

    @Override
    public int getAtomicWeight() {
        return 282;
    }
}
