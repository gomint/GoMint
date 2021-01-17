package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemStack;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.block.helper.ToolPresets;
import io.gomint.world.block.BlockLodeStone;
import io.gomint.world.block.BlockType;

/**
 * @author KingAli
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:lodestone" )
public class LodeStone extends Block implements BlockLodeStone {

    @Override
    public String blockId() {
        return "minecraft:lodestone";
    }

    @Override
    public long breakTime() {
        return 5300;
    }

    @Override
    public float blastResistance() {
        return 17.5f;
    }

    @Override
    public BlockType blockType() {
        return BlockType.LODESTONE;
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
