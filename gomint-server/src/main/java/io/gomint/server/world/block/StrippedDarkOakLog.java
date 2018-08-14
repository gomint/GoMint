package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemStack;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.block.helper.ToolPresets;
import io.gomint.world.block.BlockStrippedDarkOakLog;
import io.gomint.world.block.BlockType;

/**
 * @author Kaooot
 * @version 1.0
 */
@RegisterInfo( id = 264 )
public class StrippedDarkOakLog extends Block implements BlockStrippedDarkOakLog {

    @Override
    public int getBlockId() {
        return 264;
    }

    @Override
    public long getBreakTime() {
        return 3000;
    }

    @Override
    public Class<? extends ItemStack>[] getToolInterfaces() {
        return ToolPresets.AXE;
    }

    @Override
    public float getBlastResistance() {
        return 15.0f;
    }

    @Override
    public BlockType getType() {
        return BlockType.STRIPPED_DARK_OAK_LOG;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }
}
