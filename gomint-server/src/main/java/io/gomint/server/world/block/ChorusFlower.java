package io.gomint.server.world.block;

import io.gomint.server.world.block.helper.ToolPresets;
import io.gomint.world.block.BlockType;

import io.gomint.inventory.item.*;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockChorusFlower;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:chorus_flower" )
public class ChorusFlower extends Block implements BlockChorusFlower {

    @Override
    public String blockId() {
        return "minecraft:chorus_flower";
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
        return 2f;
    }

    @Override
    public Class<? extends ItemStack<?>>[] toolInterfaces() {
        return ToolPresets.AXE;
    }

    @Override
    public BlockType blockType() {
        return BlockType.CHORUS_FLOWER;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

}
