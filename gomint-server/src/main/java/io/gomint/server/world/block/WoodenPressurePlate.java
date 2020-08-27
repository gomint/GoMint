package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemStack;
import io.gomint.server.world.block.helper.ToolPresets;
import io.gomint.world.block.BlockType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockWoodenPressurePlate;
import io.gomint.world.block.data.LogType;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:wooden_pressure_plate", def = true)
@RegisterInfo(sId = "minecraft:jungle_pressure_plate")
@RegisterInfo(sId = "minecraft:acacia_pressure_plate")
@RegisterInfo(sId = "minecraft:birch_pressure_plate")
@RegisterInfo(sId = "minecraft:spruce_pressure_plate")
@RegisterInfo(sId = "minecraft:dark_oak_pressure_plate")
@RegisterInfo(sId = "minecraft:crimson_pressure_plate")
@RegisterInfo(sId = "minecraft:warped_pressure_plate")
public class WoodenPressurePlate extends BasePressurePlate implements BlockWoodenPressurePlate {

    private enum LogTypeMagic {
        OAK("minecraft:wooden_pressure_plate"),
        SPRUCE("minecraft:spruce_pressure_plate"),
        BIRCH("minecraft:birch_pressure_plate"),
        JUNGLE("minecraft:jungle_pressure_plate"),
        ACACIA("minecraft:acacia_pressure_plate"),
        DARK_OAK("minecraft:dark_oak_pressure_plate"),
        CRIMSON("minecraft:crimson_pressure_plate"),
        WARPED("minecraft:warped_pressure_plate");

        private final String blockId;
        LogTypeMagic(String blockId) {
            this.blockId = blockId;
        }
    }

    @Override
    public long getBreakTime() {
        return 750;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    @Override
    public boolean isSolid() {
        return false;
    }

    @Override
    public float getBlastResistance() {
        return 2.5f;
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.WOODEN_PRESSURE_PLATE;
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
        for (LogTypeMagic value : LogTypeMagic.values()) {
            if (value.blockId.equals(this.getBlockId())) {
                return LogType.valueOf(value.name());
            }
        }

        return null;
    }

    @Override
    public void setWoodType(LogType logType) {
        LogTypeMagic newState = LogTypeMagic.valueOf(logType.name());
        this.setBlockId(newState.blockId);
    }

}
