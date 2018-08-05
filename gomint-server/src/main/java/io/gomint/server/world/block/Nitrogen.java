package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockNitrogen;
import io.gomint.world.block.BlockType;

/**
 * @author Kaooot
 * @version 1.0
 */
@RegisterInfo( id = 273 )
public class Nitrogen extends BlockAtom implements BlockNitrogen {

    @Override
    public int getBlockId() {
        return 273;
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
        return BlockType.NITROGEN;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public int getProtons() {
        return 7;
    }

    @Override
    public int getElectrons() {
        return 7;
    }

    @Override
    public int getNeutrons() {
        return 7;
    }

    @Override
    public int getAtomicWeight() {
        return 14;
    }
}
