package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemStack;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.block.helper.ToolPresets;
import io.gomint.world.block.BlockType;

/**
 * @author KingAli
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:lodestone" )
public class LodeStone extends Block implements io.gomint.world.block.BlockLodeStone {

    @Override
    public String getBlockId() {
        return "minecraft:lodestone";
    }

    @Override
    public long getBreakTime() {
        return 5300;
    }

    @Override
    public float getBlastResistance() {
        return 17.5f;
    }

    @Override
    public BlockType getType() {
        return BlockType.LODESTONE;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public Class<? extends ItemStack>[] getToolInterfaces() {
        return ToolPresets.PICKAXE;
    }

}
