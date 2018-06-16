package io.gomint.server.world.block;

import io.gomint.server.world.block.helper.ToolPresets;
import io.gomint.world.block.BlockType;

import io.gomint.inventory.item.*;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 24 )
public class Sandstone extends Block implements io.gomint.world.block.BlockSandstone {

    @Override
    public int getBlockId() {
        return 24;
    }

    @Override
    public long getBreakTime() {
        return 1200;
    }

    @Override
    public Class<? extends ItemStack>[] getToolInterfaces() {
        return ToolPresets.PICKAXE;
    }

    @Override
    public float getBlastResistance() {
        return 4.0f;
    }

    @Override
    public BlockType getType() {
        return BlockType.SANDSTONE;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return false;
    }

}
