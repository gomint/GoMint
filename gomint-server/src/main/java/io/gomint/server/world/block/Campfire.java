package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemStack;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.block.helper.ToolPresets;
import io.gomint.world.block.BlockType;

/**
 * @author KingAli
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:campfire" )
public class Campfire extends Block implements io.gomint.world.block.BlockCampfire {

    @Override
    public String getBlockId() {
        return "minecraft:campfire";
    }

    @Override
    public long getBreakTime() {
        return 3000; //DEFAULT
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return false;
    }

    @Override
    public float getBlastResistance() {
        return 0; //TODO
    }

    @Override
    public Class<? extends ItemStack>[] getToolInterfaces() {
        return ToolPresets.AXE;
    }

    @Override
    public BlockType getType() {
        return BlockType.CAMPFIRE;
    }
}
