package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemStack;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.block.helper.ToolPresets;
import io.gomint.world.block.BlockType;

/**
 * @author KingAli
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:soul_campfire" )
public class SoulCampfire extends Block implements io.gomint.world.block.BlockSoulCampfire {

    @Override
    public String getBlockId() {
        return "minecraft:soul_campfire";
    }

    @Override
    public long getBreakTime() {
        return 3000;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public float getBlastResistance() {
        return 10.0f;
    }

    @Override
    public Class<? extends ItemStack>[] getToolInterfaces() {
        return ToolPresets.AXE;
    }

    @Override
    public BlockType getType() {
        return BlockType.SOUL_CAMPFIRE;
    }
}
