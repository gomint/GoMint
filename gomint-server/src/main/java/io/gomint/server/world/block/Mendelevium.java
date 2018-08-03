package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockMendelevium;
import io.gomint.world.block.BlockType;

/**
 * @author Kaooot
 * @version 1.0
 */
@RegisterInfo( id = 367 )
public class Mendelevium extends BlockAtom implements BlockMendelevium {

    @Override
    public int getBlockId() {
        return 367;
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
        return BlockType.MENDELEVIUM;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public int getProtons() {
        return 101;
    }

    @Override
    public int getElectrons() {
        return 101;
    }

    @Override
    public int getNeutrons() {
        return 155;
    }

    @Override
    public int getAtomicWeight() {
        return 258;
    }
}
