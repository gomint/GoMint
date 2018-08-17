package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockNeodymium;
import io.gomint.world.block.BlockType;

/**
 * @author Kaooot
 * @version 1.0
 */
@RegisterInfo( id = 326 )
public class Neodymium extends BlockAtom implements BlockNeodymium {

    @Override
    public int getBlockId() {
        return 326;
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
        return BlockType.NEODYMIUM;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public int getProtons() {
        return 60;
    }

    @Override
    public int getElectrons() {
        return 60;
    }

    @Override
    public int getNeutrons() {
        return 82;
    }

    @Override
    public int getAtomicWeight() {
        return 144;
    }
}
