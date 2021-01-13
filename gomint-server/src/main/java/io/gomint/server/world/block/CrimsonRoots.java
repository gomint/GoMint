package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockCrimsonRoots;
import io.gomint.world.block.BlockType;

/**
 * @author KingAli
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:crimson_roots" )
public class CrimsonRoots extends Block implements BlockCrimsonRoots {

    @Override
    public String getBlockId() {
        return "minecraft:crimson_roots";
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
        return BlockType.CRIMSON_ROOTS;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }
}
