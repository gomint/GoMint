package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemStack;
import io.gomint.server.world.block.helper.ToolPresets;
import io.gomint.world.block.BlockNetherrack;
import io.gomint.world.block.BlockType;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:netherrack" )
public class Netherrack extends Block implements BlockNetherrack {

    @Override
    public String getBlockId() {
        return "minecraft:netherrack";
    }

    @Override
    public long getBreakTime() {
        return 600;
    }

    @Override
    public float getBlastResistance() {
        return 2.0f;
    }

    @Override
    public BlockType blockType() {
        return BlockType.NETHERRACK;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public Class<? extends ItemStack>[] getToolInterfaces() {
        return ToolPresets.PICKAXE;
    }

}
