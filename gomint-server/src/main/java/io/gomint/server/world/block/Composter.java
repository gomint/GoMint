package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemStack;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.block.helper.ToolPresets;
import io.gomint.world.block.BlockComposter;
import io.gomint.world.block.BlockType;

/**
 * @author KingAli
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:composter" )
public class Composter extends Block implements BlockComposter {

    @Override
    public float getBlastResistance() {
        return 3.5f;
    }

    @Override
    public long getBreakTime() {
        return 900;
    }

    @Override
    public Class<? extends ItemStack>[] getToolInterfaces() {
        return ToolPresets.AXE;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return false;
    }

    @Override
    public BlockType blockType() {
        return BlockType.COMPOSTER;
    }
}
