package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockLivermorium;
import io.gomint.world.block.BlockType;

/**
 * @author Kaooot
 * @version 1.0
 */
@RegisterInfo( id = 382 )
public class Livermorium extends BlockAtom implements BlockLivermorium {

    @Override
    public int getBlockId() {
        return 382;
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
        return BlockType.LIVERMORIUM;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public int getProtons() {
        return 116;
    }

    @Override
    public int getElectrons() {
        return 116;
    }

    @Override
    public int getNeutrons() {
        return 177;
    }

    @Override
    public int getAtomicWeight() {
        return 293;
    }
}
