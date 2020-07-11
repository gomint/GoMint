package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockType;

/**
 * @author KingAli
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:crimson_roots" )
public class CrimsonRoots extends Block implements io.gomint.world.block.BlockCrimsonRoots {

    @Override
    public String getBlockId() {
        return "minecraft:crimson_roots";
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
    public BlockType getBlockType() {
        return BlockType.CRIMSON_ROOTS;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }
}
