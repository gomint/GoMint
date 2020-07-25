package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemStack;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.block.helper.ToolPresets;
import io.gomint.server.world.block.state.EnumBlockState;
import io.gomint.world.block.BlockPlank;
import io.gomint.world.block.BlockType;
import io.gomint.world.block.data.LogType;
import lombok.Getter;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:planks" )
public class Plank extends Block implements BlockPlank {

    @Getter
    private enum LogTypeMagic {
        OAK("oak"),
        SPRUCE("spruce"),
        BIRCH("birch"),
        JUNGLE("jungle"),
        ACACIA("acacia"),
        DARK_OAK("dark_oak"),
        CRIMSON("crimsion"),
        WARPED("warped");

        private final String value;
        LogTypeMagic(String value) {
            this.value = value;
        }
    }

    private static final EnumBlockState<LogTypeMagic, String> VARIANT = new EnumBlockState<>(v -> new String[]{"wood_type"}, LogTypeMagic.values(), LogTypeMagic::getValue, v -> {
        for (LogTypeMagic value : LogTypeMagic.values()) {
            if (value.getValue().equals(v)) {
                return value;
            }
        }

        return null;
    });

    @Override
    public String getBlockId() {
        return "minecraft:planks";
    }

    @Override
    public long getBreakTime() {
        return 3000;
    }

    @Override
    public Class<? extends ItemStack>[] getToolInterfaces() {
        return ToolPresets.AXE;
    }

    @Override
    public float getBlastResistance() {
        return 15.0f;
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.PLANK;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public LogType getPlankType() {
        return LogType.valueOf(VARIANT.getState(this).name());
    }

    @Override
    public void setPlankType(LogType logType) {
        VARIANT.setState(this, LogTypeMagic.valueOf(logType.name()));
    }

}
