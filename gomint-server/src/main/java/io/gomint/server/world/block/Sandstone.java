package io.gomint.server.world.block;

import io.gomint.server.world.block.helper.ToolPresets;
import io.gomint.world.block.BlockSandstone;
import io.gomint.world.block.BlockType;

import io.gomint.inventory.item.*;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:sandstone" )
public class Sandstone extends Block implements BlockSandstone {

    @Override
    public String getBlockId() {
        return "minecraft:sandstone";
    }

    @Override
    public long getBreakTime() {
        return 1200;
    }

    @Override
    public Class<? extends ItemStack>[] getToolInterfaces() {
        return ToolPresets.PICKAXE;
    }

    @Override
    public float getBlastResistance() {
        return 4.0f;
    }

    @Override
    public BlockType blockType() {
        return BlockType.SANDSTONE;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return false;
    }

}
