package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockType;

/**
 * @author KingAli
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:crimson_fungus" )
public class CrimsonFungus extends Block implements io.gomint.world.block.BlockCrimsonFungus {

    @Override
    public String getBlockId() {
        return "minecraft:crimson_fungus";
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
        return BlockType.CRIMSON_FUNGUS;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }
}
