package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemStack;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.block.helper.ToolPresets;
import io.gomint.server.world.block.state.EnumBlockState;
import io.gomint.world.block.BlockPlank;
import io.gomint.world.block.BlockType;
import io.gomint.world.block.data.LogType;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:planks", def = true )
@RegisterInfo( sId = "minecraft:crimson_planks" )
@RegisterInfo( sId = "minecraft:warped_planks" )
public class Plank extends Block implements BlockPlank {

    private static final String PLANK_ID = "minecraft:planks";

    private enum LogTypeMagic {
        OAK(PLANK_ID,"oak"),
        SPRUCE(PLANK_ID,"spruce"),
        BIRCH(PLANK_ID,"birch"),
        JUNGLE(PLANK_ID,"jungle"),
        ACACIA(PLANK_ID,"acacia"),
        DARK_OAK(PLANK_ID,"dark_oak"),
        CRIMSON("minecraft:crimson_planks", ""),
        WARPED("minecraft:warped_planks", "");

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
        switch (this.getBlockId()) {
            case "minecraft:crimson_planks":
                return LogType.CRIMSON;
            case "minecraft:warped_planks":
                return LogType.WARPED;
        }

        return LogType.valueOf(VARIANT.getState(this).name());
    }

    @Override
    public void setPlankType(LogType logType) {
        LogTypeMagic newState = LogTypeMagic.valueOf(logType.name());

        if (!newState.value.isEmpty()) {
            VARIANT.setState(this, newState);
        }

        if (!this.getBlockId().equals(newState.blockId)) {
            this.setBlockId(newState.blockId);
        }
    }

}
