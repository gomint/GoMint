package io.gomint.server.world.block;

import io.gomint.server.world.block.helper.ToolPresets;
import io.gomint.world.block.BlockStonecutter;
import io.gomint.world.block.BlockType;

import io.gomint.inventory.item.*;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:stonecutter" )
public class Stonecutter extends Block implements BlockStonecutter {

    @Override
    public String blockId() {
        return "minecraft:stonecutter";
    }

    @Override
    public long breakTime() {
        return 5250;
    }

    @Override
    public float blastResistance() {
        return 17.5f;
    }

    @Override
    public Class<? extends ItemStack<?>>[] toolInterfaces() {
        return ToolPresets.PICKAXE;
    }

    @Override
    public BlockType blockType() {
        return BlockType.STONECUTTER;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

}
