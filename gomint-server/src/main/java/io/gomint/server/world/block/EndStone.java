package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemStack;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.block.helper.ToolPresets;
import io.gomint.world.block.BlockEndStone;
import io.gomint.world.block.BlockType;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:end_stone" )
public class EndStone extends Block implements BlockEndStone {

    @Override
    public String blockId() {
        return "minecraft:end_stone";
    }

    @Override
    public long breakTime() {
        return 4500;
    }

    @Override
    public float blastResistance() {
        return 45.0f;
    }

    @Override
    public BlockType blockType() {
        return BlockType.END_STONE;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public Class<? extends ItemStack<?>>[] toolInterfaces() {
        return ToolPresets.PICKAXE;
    }

}
