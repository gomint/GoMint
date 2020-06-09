package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemStack;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.block.helper.ToolPresets;
import io.gomint.server.world.block.state.AxisBlockState;
import io.gomint.server.world.block.state.EnumBlockState;
import io.gomint.world.block.BlockType;
import io.gomint.world.block.data.Axis;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Collections;
import java.util.List;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:quartz_block")
@EqualsAndHashCode(callSuper = true)
public class BlockOfQuartz extends Block implements io.gomint.world.block.BlockBlockOfQuartz {

    @Getter
    private enum VariantMagic {
        SMOOTH("smooth"),
        LINES("lines"),
        DEFAULT("default"),
        CHISELED("chiseled");

        private final String value;

        VariantMagic(String value) {
            this.value = value;
        }
    }

    private final AxisBlockState axis = new AxisBlockState(this, () -> "pillar_axis");
    private final EnumBlockState<VariantMagic, String> variant = new EnumBlockState<>(this, () -> "chisel_type", VariantMagic.values(), VariantMagic::getValue, v -> {
        for (VariantMagic value : VariantMagic.values()) {
            if (value.getValue().equals(v)) {
                return value;
            }
        }

        return null;
    });

    @Override
    public String getBlockId() {
        return "minecraft:quartz_block";
    }

    @Override
    public long getBreakTime() {
        return 1200;
    }

    @Override
    public float getBlastResistance() {
        return 4.0f;
    }

    @Override
    public BlockType getType() {
        return BlockType.BLOCK_OF_QUARTZ;
    }

    @Override
    public Variant getVariant() {
        return Variant.valueOf(this.variant.getState().name());
    }

    @Override
    public void setVariant(Variant variant) {
        this.variant.setState(VariantMagic.valueOf(variant.name()));
    }

    @Override
    public Class<? extends ItemStack>[] getToolInterfaces() {
        return ToolPresets.PICKAXE;
    }

    @Override
    public List<ItemStack> getDrops(ItemStack itemInHand) {
        if (isCorrectTool(itemInHand)) {
            return super.getDrops(itemInHand);
        }

        return Collections.emptyList();
    }

    @Override
    public void setAxis(Axis axis) {
        this.axis.setState(axis);
    }

    @Override
    public Axis getAxis() {
        return this.axis.getState();
    }

}
