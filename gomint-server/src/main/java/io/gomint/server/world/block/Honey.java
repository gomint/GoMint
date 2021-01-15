package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockHoney;
import io.gomint.world.block.BlockType;

/**
 * @author KingAli
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:honey_block" )
public class Honey extends Block implements BlockHoney {

    @Override
    public String getBlockId() {
        return "minecraft:honey_block";
    }

    @Override
    public long breakTime() {
        return 0;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public float getBlastResistance() {
        return 0;
    }

    @Override
    public BlockType blockType() {
        return BlockType.HONEY_BLOCK;
    }
}
