package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemStack;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.block.helper.ToolPresets;
import io.gomint.world.block.BlockSoulSoil;
import io.gomint.world.block.BlockType;

/**
 * @author KingAli
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:soul_soil" )
public class SoulSoil extends Block implements BlockSoulSoil {

    @Override
    public String blockId() {
        return "minecraft:soul_soil";
    }

    @Override
    public long breakTime() {
        return 800;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public float blastResistance() {
        return 2.5f;
    }

    @Override
    public Class<? extends ItemStack<?>>[] toolInterfaces() {
        return ToolPresets.SHOVEL;
    }

    @Override
    public BlockType blockType() {
        return BlockType.SOUL_SOIL;
    }


}
