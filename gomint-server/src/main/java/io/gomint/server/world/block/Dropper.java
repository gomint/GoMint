package io.gomint.server.world.block;

import io.gomint.server.world.block.helper.ToolPresets;
import io.gomint.world.block.BlockType;

import io.gomint.inventory.item.*;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:dropper" )
public class Dropper extends Block implements io.gomint.world.block.BlockDropper {

    @Override
    public String getBlockId() {
        return "minecraft:dropper";
    }

    @Override
    public long getBreakTime() {
        return 5250;
    }

    @Override
    public float getBlastResistance() {
        return 17.5f;
    }

    @Override
    public Class<? extends ItemStack>[] getToolInterfaces() {
        return ToolPresets.PICKAXE;
    }

    @Override
    public BlockType getType() {
        return BlockType.DROPPER;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

}
