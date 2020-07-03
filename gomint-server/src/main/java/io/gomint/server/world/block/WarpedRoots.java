package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockType;

/**
 * @author KingAli
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:warped_roots" )
public class WarpedRoots extends Block implements io.gomint.world.block.BlockWarpedRoots {

    @Override
    public String getBlockId() {
        return "minecraft:warped_roots";
    }

    @Override
    public boolean isTransparent() {
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
    public BlockType getType() {
        return BlockType.WARPED_ROOTS;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }
}
