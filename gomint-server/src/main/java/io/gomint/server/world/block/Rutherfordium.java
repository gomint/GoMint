package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockRutherfordium;
import io.gomint.world.block.BlockType;

/**
 * @author Kaooot
 * @version 1.0
 */
@RegisterInfo( id = 370 )
public class Rutherfordium extends BlockAtom implements BlockRutherfordium {

    @Override
    public int getBlockId() {
        return 370;
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
        return BlockType.RUTHERFORDIUM;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public int getProtons() {
        return 104;
    }

    @Override
    public int getElectrons() {
        return 104;
    }

    @Override
    public int getNeutrons() {
        return 163;
    }

    @Override
    public int getAtomicWeight() {
        return 267;
    }
}
