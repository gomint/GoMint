package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemStack;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.block.state.EnumBlockState;
import io.gomint.world.block.BlockStainedGlassPane;
import io.gomint.world.block.BlockType;
import io.gomint.world.block.data.BlockColor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:stained_glass_pane")
public class StainedGlassPane extends Block implements BlockStainedGlassPane {

    private static final EnumBlockState<BlockColor, String> COLOR = new EnumBlockState<>(v -> new String[]{"color"}, BlockColor.values(), e -> e.name().toLowerCase(), v -> BlockColor.valueOf(v.toUpperCase()));

    @Override
    public String getBlockId() {
        return "minecraft:stained_glass_pane";
    }

    @Override
    public long getBreakTime() {
        return 450;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public List<ItemStack> getDrops(ItemStack itemInHand) {
        return new ArrayList<>();
    }

    @Override
    public float getBlastResistance() {
        return 1.5f;
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.STAINED_GLASS_PANE;
    }

    @Override
    public BlockColor getColor() {
        return COLOR.getState(this);
    }

    @Override
    public void setColor(BlockColor color) {
        COLOR.setState(this, color);
    }

}
