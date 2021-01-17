package io.gomint.server.world.block;

import io.gomint.server.world.block.helper.ToolPresets;
import io.gomint.world.block.BlockChorusPlant;
import io.gomint.world.block.BlockType;

import io.gomint.inventory.item.*;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:chorus_plant" )
public class ChorusPlant extends Block implements BlockChorusPlant {

    @Override
    public String blockId() {
        return "minecraft:chorus_plant";
    }

    @Override
    public long breakTime() {
        return 600;
    }

    @Override
    public boolean transparent() {
        return true;
    }

    @Override
    public float blastResistance() {
        return 2.0f;
    }

    @Override
    public Class<? extends ItemStack<?>>[] toolInterfaces() {
        return ToolPresets.AXE;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }
    @Override
    public BlockType blockType() {
        return BlockType.CHORUS_PLANT;
    }



}
