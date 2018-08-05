package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockSelenium;
import io.gomint.world.block.BlockType;

/**
 * @author Kaooot
 * @version 1.0
 */
@RegisterInfo( id = 300 )
public class Selenium extends BlockAtom implements BlockSelenium {

    @Override
    public int getBlockId() {
        return 300;
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
        return BlockType.SELENIUM;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public int getProtons() {
        return 34;
    }

    @Override
    public int getElectrons() {
        return 34;
    }

    @Override
    public int getNeutrons() {
        return 40;
    }

    @Override
    public int getAtomicWeight() {
        return 79;
    }
}
