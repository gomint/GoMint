package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.Block;
import io.gomint.world.block.data.LogType;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:wooden_button", id = 143, def = true)
@RegisterInfo(sId = "minecraft:acacia_button", id = -140)
@RegisterInfo(sId = "minecraft:birch_button", id = -141)
@RegisterInfo(sId = "minecraft:dark_oak_button", id = -142)
@RegisterInfo(sId = "minecraft:jungle_button", id = -143)
@RegisterInfo(sId = "minecraft:spruce_button", id = -144)
@RegisterInfo(sId = "minecraft:crimson_button", id = -260)
@RegisterInfo(sId = "minecraft:warped_button", id = -261)
public class ItemWoodenButton extends ItemStack implements io.gomint.inventory.item.ItemWoodenButton {

    @Override
    public long getBurnTime() {
        return 15000;
    }

    @Override
    public ItemType getItemType() {
        return ItemType.WOODEN_BUTTON;
    }

    @Override
    public LogType getWoodType() {
        switch (this.getMaterial()) {
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

}
