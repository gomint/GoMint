package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockBromine;
import io.gomint.world.block.BlockType;

/**
 * @author Kaooot
 * @version 1.0
 */
@RegisterInfo( id = 301 )
public class Bromine extends BlockAtom implements BlockBromine {

    @Override
    public int getBlockId() {
        return 301;
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
        return BlockType.BROMINE;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public int getProtons() {
        return 25;
    }

    @Override
    public int getElectrons() {
        return 25;
    }

    @Override
    public int getNeutrons() {
        return 44;
    }

    @Override
    public int getAtomicWeight() {
        return 80;
    }
}
