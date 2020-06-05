package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemStack;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.block.helper.ToolPresets;
import io.gomint.world.block.BlockType;

/**
 * @author KingAli
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:lectern" )
public class Lectern extends Block implements io.gomint.world.block.BlockLectern {

    @Override
    public String getBlockId() {
        return "minecraft:lectern";
    }

    @Override
    public long getBreakTime() {
        return 3750;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return false;
    }

    @Override
    public float getBlastResistance() {
        return 12.5f;
    }

    @Override
    public BlockType getType() {
        return BlockType.LECTERN;
    }

    @Override
    public Class<? extends ItemStack>[] getToolInterfaces() {
        return ToolPresets.AXE;
    }
}
