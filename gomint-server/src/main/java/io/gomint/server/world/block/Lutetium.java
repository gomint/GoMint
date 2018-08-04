package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockLutetium;
import io.gomint.world.block.BlockType;

/**
 * @author Kaooot
 * @version 1.0
 */
@RegisterInfo( id = 337 )
public class Lutetium extends BlockAtom implements BlockLutetium {

    @Override
    public int getBlockId() {
        return 337;
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
        return BlockType.LUTETIUM;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public int getProtons() {
        return 71;
    }

    @Override
    public int getElectrons() {
        return 71;
    }

    @Override
    public int getNeutrons() {
        return 104;
    }

    @Override
    public int getAtomicWeight() {
        return 175;
    }
}
