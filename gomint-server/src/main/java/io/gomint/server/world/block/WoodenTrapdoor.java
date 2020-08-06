package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemStack;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.block.helper.ToolPresets;
import io.gomint.world.block.BlockType;
import io.gomint.world.block.BlockWoodenTrapdoor;
import io.gomint.world.block.data.LogType;
import lombok.Getter;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:trapdoor", def = true )
@RegisterInfo(sId = "minecraft:jungle_trapdoor")
@RegisterInfo(sId = "minecraft:spruce_trapdoor")
@RegisterInfo(sId = "minecraft:acacia_trapdoor")
@RegisterInfo(sId = "minecraft:dark_oak_trapdoor")
@RegisterInfo(sId = "minecraft:birch_trapdoor")
public class WoodenTrapdoor extends Trapdoor implements BlockWoodenTrapdoor {

    @Getter
    private enum LogTypeMagic {
        OAK("minecraft:trapdoor"),
        SPRUCE("minecraft:spruce_trapdoor"),
        BIRCH("minecraft:birch_trapdoor"),
        JUNGLE("minecraft:jungle_trapdoor"),
        ACACIA("minecraft:acacia_trapdoor"),
        DARK_OAK("minecraft:dark_oak_trapdoor"),
        CRIMSON("minecraft:crimson_trapdoor"),
        WARPED("minecraft:warped_trapdoor");

        private final String blockId;
        LogTypeMagic(String blockId) {
            this.blockId = blockId;
        }
    }

    @Override
    public long getBreakTime() {
        return 4500;
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
        return BlockType.TRAPDOOR;
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
            if (value.getBlockId().equals(this.getBlockId())) {
                return LogType.valueOf(value.name());
            }
        }

        return null;
    }

    @Override
    public void setWoodType(LogType logType) {
        LogTypeMagic newState = LogTypeMagic.valueOf(logType.name());
        this.setBlockId(newState.getBlockId());
    }

}
