package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockHoneyComb;
import io.gomint.world.block.BlockType;

/**
 * @author KingAli
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:honeycomb_block" )
public class HoneyComb extends Block implements BlockHoneyComb {

    @Override
    public String getBlockId() {
        return "minecraft:honeycomb_block";
    }

    @Override
    public long getBreakTime() {
        return 900;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return false;
    }

    @Override
    public float getBlastResistance() {
        return 3;
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.HONEYCOMB;
    }
}
