package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemStack;
import io.gomint.server.world.block.helper.ToolPresets;

/**
 * @author geNAZt
 * @version 1.0
 */
public abstract class Banner extends Block {

    @Override
    public float getBlastResistance() {
        return 5f;
    }

    @Override
    public long getBreakTime() {
        return 1500;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public Class<? extends ItemStack>[] getToolInterfaces() {
        return ToolPresets.AXE;
    }
}
