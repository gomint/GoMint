package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockKrypton;
import io.gomint.world.block.BlockType;

/**
 * @author Kaooot
 * @version 1.0
 */
@RegisterInfo( id = 302 )
public class Krypton extends BlockAtom implements BlockKrypton {

    @Override
    public int getBlockId() {
        return 302;
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
        return BlockType.KRYPTON;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public int getProtons() {
        return 36;
    }

    @Override
    public int getElectrons() {
        return 36;
    }

    @Override
    public int getNeutrons() {
        return 42;
    }

    @Override
    public int getAtomicWeight() {
        return 84;
    }
}
