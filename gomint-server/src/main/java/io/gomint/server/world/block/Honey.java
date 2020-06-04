package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemStack;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.block.helper.ToolPresets;
import io.gomint.world.block.BlockType;

/**
 * @author KingAli
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:honey_block" )
public class Honey extends Block implements io.gomint.world.block.BlockHoney {

    @Override
    public String getBlockId() {
        return "minecraft:honey_block";
    }

    @Override
    public long getBreakTime() {
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
    public BlockType getType() {
        return BlockType.HONEY_BLOCK;
    }
}
