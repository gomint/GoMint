package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemStack;
import io.gomint.server.world.block.helper.ToolPresets;
import io.gomint.world.block.BlockMossyCobblestone;
import io.gomint.world.block.BlockType;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:mossy_cobblestone" )
public class MossyCobblestone extends Block implements BlockMossyCobblestone {

    @Override
    public String getBlockId() {
        return "minecraft:mossy_cobblestone";
    }

    @Override
    public long breakTime() {
        return 3000;
    }

    @Override
    public float getBlastResistance() {
        return 10.0f;
    }

    @Override
    public BlockType blockType() {
        return BlockType.MOSSY_COBBLESTONE;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public Class<? extends ItemStack<?>>[] getToolInterfaces() {
        return ToolPresets.PICKAXE;
    }

}
