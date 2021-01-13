package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockType;
import io.gomint.world.block.BlockWarpedRoots;

/**
 * @author KingAli
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:warped_roots" )
public class WarpedRoots extends Block implements BlockWarpedRoots {

    @Override
    public String getBlockId() {
        return "minecraft:warped_roots";
    }

    @Override
    public boolean transparent() {
        return true;
    }

    @Override
    public float getBlastResistance() {
        return 0;
    }

    @Override
    public long getBreakTime() {
        return 0;
    }

    @Override
    public BlockType blockType() {
        return BlockType.WARPED_ROOTS;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }
}
