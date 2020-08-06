package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemStack;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.block.helper.ToolPresets;
import io.gomint.world.block.BlockSoulLantern;
import io.gomint.world.block.BlockType;

/**
 * @author KingAli
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:soul_lantern" )
public class SoulLantern extends Block implements BlockSoulLantern {

    @Override
    public String getBlockId() {
        return "minecraft:soul_lantern";
    }

    @Override
    public long getBreakTime() {
        return 5300;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
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
    public BlockType getBlockType() {
        return BlockType.SOUL_LANTERN;
    }
}
