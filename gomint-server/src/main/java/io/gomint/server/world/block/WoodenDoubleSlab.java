package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemStack;
import io.gomint.server.world.block.helper.ToolPresets;
import io.gomint.server.world.block.state.BooleanBlockState;
import io.gomint.server.world.block.state.EnumBlockState;
import io.gomint.world.block.BlockType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockWoodenDoubleSlab;
import io.gomint.world.block.data.LogType;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:double_wooden_slab", def = true )
@RegisterInfo( sId = "minecraft:crimson_double_slab" )
@RegisterInfo( sId = "minecraft:warped_double_slab")
public class WoodenDoubleSlab extends Block implements BlockWoodenDoubleSlab {

    // This blockstate is not exposed since its BS ( crimson and warped double slabs have it for whatever reasons )
    private static final BooleanBlockState TOP = new BooleanBlockState(() -> new String[]{"top_slot_bit"});

    private static final String WOODEN_ID = "minecraft:double_wooden_slab";

    private enum LogTypeMagic {
        OAK(WOODEN_ID, "oak"),
        SPRUCE(WOODEN_ID,"spruce"),
        BIRCH(WOODEN_ID,"birch"),
        JUNGLE(WOODEN_ID,"jungle"),
        ACACIA(WOODEN_ID,"acacia"),
        DARK_OAK(WOODEN_ID,"dark_oak"),
        CRIMSON("minecraft:crimson_double_slab", ""),
        WARPED("minecraft:warped_double_slab", "");

        private final String blockId;
        private final String value;

        LogTypeMagic(String blockId, String value) {
            this.blockId = blockId;
            this.value = value;
        }
    }

    private static final EnumBlockState<LogTypeMagic, String> VARIANT = new EnumBlockState<>(v -> new String[]{"wood_type"}, LogTypeMagic.values(), v -> v.value, v -> {
        for (LogTypeMagic value : LogTypeMagic.values()) {
            if (value.value.equals(v)) {
                return value;
            }
        }

        return null;
    });

    @Override
    public long breakTime() {
        return 3000;
    }

    @Override
    public boolean transparent() {
        return true;
    }

    @Override
    public float blastResistance() {
        return 15.0f;
    }

    @Override
    public BlockType blockType() {
        return BlockType.WOODEN_DOUBLE_SLAB;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public Class<? extends ItemStack<?>>[] toolInterfaces() {
        return ToolPresets.AXE;
    }

    @Override
    public LogType type() {
        switch (this.blockId()) {
            case "minecraft:crimson_slab":
                return LogType.CRIMSON;
            case "minecraft:warped_slab":
                return LogType.WARPED;
        }

        return LogType.valueOf(VARIANT.state(this).name());
    }

    @Override
    public BlockWoodenDoubleSlab type(LogType logType) {
        LogTypeMagic newState = LogTypeMagic.valueOf(logType.name());

        if (!newState.value.isEmpty()) {
            VARIANT.state(this, newState);
        }

        if (!this.blockId().equals(newState.blockId)) {
            this.blockId(newState.blockId);
        }

        return this;
    }

}
