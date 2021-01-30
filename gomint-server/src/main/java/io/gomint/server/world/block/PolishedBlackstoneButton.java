package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemStack;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.block.helper.ToolPresets;
import io.gomint.world.block.BlockPolishedBlackstoneButton;
import io.gomint.world.block.BlockType;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:polished_blackstone_button")
public class PolishedBlackstoneButton extends Button<BlockPolishedBlackstoneButton> implements BlockPolishedBlackstoneButton {

    @Override
    public long breakTime() {
        return 750;
    }

    @Override
    public boolean transparent() {
        return true;
    }

    @Override
    public boolean solid() {
        return false;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return false;
    }

    @Override
    public float blastResistance() {
        return 2.5f;
    }

    @Override
    public BlockType blockType() {
        return BlockType.POLISHED_BLACKSTONE_BUTTON;
    }

    @Override
    public Class<? extends ItemStack<?>>[] toolInterfaces() {
        return ToolPresets.PICKAXE;
    }

}
