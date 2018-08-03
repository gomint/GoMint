package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockOxygen;
import io.gomint.world.block.BlockType;

/**
 * @author Kaooot
 * @version 1.0
 */
@RegisterInfo( id = 274 )
public class Oxygen extends BlockAtom implements BlockOxygen {

    @Override
    public int getBlockId() {
        return 274;
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
        return BlockType.OXYGEN;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public int getProtons() {
        return 8;
    }

    @Override
    public int getElectrons() {
        return 8;
    }

    @Override
    public int getNeutrons() {
        return 8;
    }

    @Override
    public int getAtomicWeight() {
        return 16;
    }
}
