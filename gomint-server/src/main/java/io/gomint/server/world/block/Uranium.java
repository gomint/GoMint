package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockType;
import io.gomint.world.block.BlockUranium;

/**
 * @author Kaooot
 * @version 1.0
 */
@RegisterInfo( id = 358 )
public class Uranium extends BlockAtom implements BlockUranium {

    @Override
    public int getBlockId() {
        return 358;
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
        return BlockType.URANIUM;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public int getProtons() {
        return 92;
    }

    @Override
    public int getElectrons() {
        return 92;
    }

    @Override
    public int getNeutrons() {
        return 141;
    }

    @Override
    public int getAtomicWeight() {
        return 238;
    }
}
