package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemStack;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.block.helper.ToolPresets;
import io.gomint.world.block.BlockBasalt;
import io.gomint.world.block.BlockType;

/**
 * @author KingAli
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:basalt" )
@RegisterInfo(sId = "minecraft:polished_basalt" ) //TODO Item, BlockState
public class Basalt extends Block implements BlockBasalt {

    @Override
    public String getBlockId() {
        return "minecraft:basalt";
    }

    @Override
    public long breakTime() {
        return 1900;
    }

    @Override
    public Class<? extends ItemStack<?>>[] getToolInterfaces() {
        return ToolPresets.PICKAXE;
    }

    @Override
    public float getBlastResistance() {
        return 7.0f;
    }

    @Override
    public BlockType blockType() {
        return BlockType.BASALT;
    }
}
