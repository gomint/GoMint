package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockChromium;
import io.gomint.world.block.BlockType;

/**
 * @author Kaooot
 * @version 1.0
 */
@RegisterInfo( id = 290 )
public class Chromium extends BlockAtom implements BlockChromium {

    @Override
    public int getBlockId() {
        return 290;
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
        return BlockType.CHROMIUM;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public int getProtons() {
        return 24;
    }

    @Override
    public int getElectrons() {
        return 24;
    }

    @Override
    public int getNeutrons() {
        return 26;
    }

    @Override
    public int getAtomicWeight() {
        return 52;
    }
}
