package io.gomint.server.world.block;

import io.gomint.server.world.block.helper.ToolPresets;
import io.gomint.world.block.BlockType;

import io.gomint.inventory.item.*;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:chorus_plant" )
public class ChorusPlant extends Block implements io.gomint.world.block.BlockChorusPlant {

    @Override
    public String getBlockId() {
        return "minecraft:chorus_plant";
    }

    @Override
    public long getBreakTime() {
        return 600;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    @Override
    public float getBlastResistance() {
        return 2.0f;
    }

    @Override
    public Class<? extends ItemStack>[] getToolInterfaces() {
        return ToolPresets.AXE;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }
    @Override
    public BlockType getBlockType() {
        return BlockType.CHORUS_PLANT;
    }



}
