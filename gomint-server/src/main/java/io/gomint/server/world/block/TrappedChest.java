package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemStack;
import io.gomint.server.world.block.helper.ToolPresets;
import io.gomint.world.block.BlockType;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:trapped_chest" )
public class TrappedChest extends Block implements io.gomint.world.block.BlockTrappedChest {

    @Override
    public String getBlockId() {
        return "minecraft:trapped_chest";
    }

    @Override
    public long getBreakTime() {
        return 3750;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    @Override
    public float getBlastResistance() {
        return 12.5f;
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.TRAPPED_CHEST;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public Class<? extends ItemStack>[] getToolInterfaces() {
        return ToolPresets.AXE;
    }

}
