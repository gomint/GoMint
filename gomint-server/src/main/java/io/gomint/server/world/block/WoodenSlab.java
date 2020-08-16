package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemStack;
import io.gomint.server.world.block.helper.ToolPresets;
import io.gomint.server.world.block.state.EnumBlockState;
import io.gomint.world.block.BlockType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockWoodenSlab;
import io.gomint.world.block.data.LogType;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:wooden_slab", def = true)
@RegisterInfo(sId = "minecraft:warped_slab")
@RegisterInfo(sId = "minecraft:crimson_slab")
public class WoodenSlab extends Slab implements BlockWoodenSlab {

    private static final String WOODEN_ID = "minecraft:wooden_slab";

    private enum LogTypeMagic {
        OAK(WOODEN_ID, "oak"),
        SPRUCE(WOODEN_ID,"spruce"),
        BIRCH(WOODEN_ID,"birch"),
        JUNGLE(WOODEN_ID,"jungle"),
        ACACIA(WOODEN_ID,"acacia"),
        DARK_OAK(WOODEN_ID,"dark_oak"),
        CRIMSON("minecraft:crimson_slab", ""),
        WARPED("minecraft:warped_slab", "");

        private final String blockId;
        private final String value;

        LogTypeMagic(String blockId, String value) {
            this.blockId = blockId;
            this.value = value;
        }
    }

    private static final EnumBlockState<LogTypeMagic, String> VARIANT = new EnumBlockState<>( v -> new String[]{"wood_type"}, LogTypeMagic.values(), v -> v.value, v -> {
        for (LogTypeMagic value : LogTypeMagic.values()) {
            if (value.value.equals(v)) {
                return value;
            }
        }

        return null;
    });

    @Override
    public long getBreakTime() {
        return 3000;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    @Override
    public float getBlastResistance() {
        return 15.0f;
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.WOODEN_SLAB;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public Class<? extends ItemStack>[] getToolInterfaces() {
        return ToolPresets.AXE;
    }

    @Override
    public LogType getWoodType() {
        switch (this.getBlockId()) {
            case "minecraft:crimson_slab":
                return LogType.CRIMSON;
            case "minecraft:warped_slab":
                return LogType.WARPED;
        }

        return LogType.valueOf(VARIANT.getState(this).name());
    }

    @Override
    public void setWoodType(LogType logType) {
        LogTypeMagic newState = LogTypeMagic.valueOf(logType.name());

        if (!newState.value.isEmpty()) {
            VARIANT.setState(this, newState);
        }

        if (!this.getBlockId().equals(newState.blockId)) {
            this.setBlockId(newState.blockId);
        }
    }

}
