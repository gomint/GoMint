package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockCesium;
import io.gomint.world.block.BlockType;

/**
 * @author Kaooot
 * @version 1.0
 */
@RegisterInfo( id = 321 )
public class Cesium extends BlockAtom implements BlockCesium {

    @Override
    public int getBlockId() {
        return 321;
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
        return BlockType.CESIUM;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public int getProtons() {
        return 55;
    }

    @Override
    public int getElectrons() {
        return 55;
    }

    @Override
    public int getNeutrons() {
        return 74;
    }

    @Override
    public int getAtomicWeight() {
        return 133;
    }
}
