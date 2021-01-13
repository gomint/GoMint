package io.gomint.server.world.block;

import io.gomint.world.block.BlockGlassPane;
import io.gomint.world.block.BlockType;

import io.gomint.inventory.item.ItemStack;
import io.gomint.server.registry.RegisterInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:glass_pane" )
public class GlassPane extends Block implements BlockGlassPane {

    @Override
    public String getBlockId() {
        return "minecraft:glass_pane";
    }

    @Override
    public long getBreakTime() {
        return 450;
    }

    @Override
    public boolean transparent() {
        return true;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public List<ItemStack> drops(ItemStack itemInHand ) {
        return new ArrayList<>();
    }

    @Override
    public float getBlastResistance() {
        return 1.5f;
    }

    @Override
    public BlockType blockType() {
        return BlockType.GLASS_PANE;
    }

}
