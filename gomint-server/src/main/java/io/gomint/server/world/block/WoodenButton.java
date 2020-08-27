package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemStack;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.block.helper.ToolPresets;
import io.gomint.world.block.BlockType;
import io.gomint.world.block.BlockWoodenButton;
import io.gomint.world.block.data.LogType;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:wooden_button", def = true)
@RegisterInfo(sId = "minecraft:spruce_button")
@RegisterInfo(sId = "minecraft:birch_button")
@RegisterInfo(sId = "minecraft:dark_oak_button")
@RegisterInfo(sId = "minecraft:jungle_button")
@RegisterInfo(sId = "minecraft:acacia_button")
@RegisterInfo(sId = "minecraft:warped_button")
@RegisterInfo(sId = "minecraft:crimson_button")
public class WoodenButton extends Button implements BlockWoodenButton {

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
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public LogType getWoodType() {
        switch (this.getBlockId()) {
            case "minecraft:crimson_button":
                return LogType.CRIMSON;
            case "minecraft:warped_button":
                return LogType.WARPED;
            case "minecraft:wooden_button":
                return LogType.OAK;
            case "minecraft:spruce_button":
                return LogType.SPRUCE;
            case "minecraft:birch_button":
                return LogType.BIRCH;
            case "minecraft:dark_oak_button":
                return LogType.DARK_OAK;
            case "minecraft:jungle_button":
                return LogType.JUNGLE;
            case "minecraft:acacia_button":
                return LogType.ACACIA;
        }

        return LogType.OAK;
    }

    @Override
    public void setWoodType(LogType logType) {
        switch (logType) {
            case CRIMSON:
                this.setBlockId("minecraft:crimson_button");
                break;
            case WARPED:
                this.setBlockId("minecraft:warped_button");
                break;
            case OAK:
                this.setBlockId("minecraft:wooden_button");
                break;
            case SPRUCE:
                this.setBlockId("minecraft:spruce_button");
                break;
            case BIRCH:
                this.setBlockId("minecraft:birch_button");
                break;
            case DARK_OAK:
                this.setBlockId("minecraft:dark_oak_button");
                break;
            case JUNGLE:
                this.setBlockId("minecraft:jungle_button");
                break;
            case ACACIA:
                this.setBlockId("minecraft:acacia_button");
                break;
        }
    }

    @Override
    public float getBlastResistance() {
        return 2.5f;
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.WOODEN_BUTTON;
    }

    @Override
    public Class<? extends ItemStack>[] getToolInterfaces() {
        return ToolPresets.AXE;
    }

}
