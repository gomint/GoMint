package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.data.LogType;

import java.time.Duration;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:wooden_button", def = true)
@RegisterInfo(sId = "minecraft:acacia_button")
@RegisterInfo(sId = "minecraft:birch_button")
@RegisterInfo(sId = "minecraft:dark_oak_button")
@RegisterInfo(sId = "minecraft:jungle_button")
@RegisterInfo(sId = "minecraft:spruce_button")
@RegisterInfo(sId = "minecraft:crimson_button")
@RegisterInfo(sId = "minecraft:warped_button")
public class ItemWoodenButton extends ItemStack< io.gomint.inventory.item.ItemWoodenButton> implements io.gomint.inventory.item.ItemWoodenButton {

    @Override
    public Duration burnTime() {
        return Duration.ofMillis(15000);
    }

    @Override
    public ItemType itemType() {
        return ItemType.WOODEN_BUTTON;
    }

    @Override
    public LogType type() {
        switch (this.material()) {
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
    public ItemWoodenButton type(LogType logType) {
        switch (logType) {
            case CRIMSON:
                this.blockId("minecraft:crimson_button");
                break;
            case WARPED:
                this.blockId("minecraft:warped_button");
                break;
            case OAK:
                this.blockId("minecraft:wooden_button");
                break;
            case SPRUCE:
                this.blockId("minecraft:spruce_button");
                break;
            case BIRCH:
                this.blockId("minecraft:birch_button");
                break;
            case DARK_OAK:
                this.blockId("minecraft:dark_oak_button");
                break;
            case JUNGLE:
                this.blockId("minecraft:jungle_button");
                break;
            case ACACIA:
                this.blockId("minecraft:acacia_button");
                break;
        }

        return this;
    }

}
