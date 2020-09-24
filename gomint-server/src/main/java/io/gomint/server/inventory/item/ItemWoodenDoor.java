package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.Block;
import io.gomint.world.block.BlockWoodenDoor;
import io.gomint.world.block.data.LogType;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:wooden_door", id = 324, def = true)
@RegisterInfo(sId = "minecraft:acacia_door", id = 430)
@RegisterInfo(sId = "minecraft:birch_door", id = 428)
@RegisterInfo(sId = "minecraft:dark_oak_door", id = 431)
@RegisterInfo(sId = "minecraft:jungle_door", id = 429)
@RegisterInfo(sId = "minecraft:warped_door", id = 756)
@RegisterInfo(sId = "minecraft:crimson_door", id = 755)
@RegisterInfo(sId = "minecraft:spruce_door", id = 427)
public class ItemWoodenDoor extends ItemStack implements io.gomint.inventory.item.ItemWoodenDoor {

    @Override
    public long getBurnTime() {
        return 10000;
    }

    @Override
    public ItemType getItemType() {
        return ItemType.WOODEN_DOOR;
    }

    @Override
    public LogType getWoodType() {
        switch (this.getMaterial()) {
            case "minecraft:crimson_door":
                return LogType.CRIMSON;
            case "minecraft:warped_door":
                return LogType.WARPED;
            case "minecraft:dark_oak_door":
                return LogType.DARK_OAK;
            case "minecraft:acacia_door":
                return LogType.ACACIA;
            case "minecraft:jungle_door":
                return LogType.JUNGLE;
            case "minecraft:birch_door":
                return LogType.BIRCH;
            case "minecraft:spruce_door":
                return LogType.SPRUCE;
            case "minecraft:wooden_door":
            default:
                return LogType.OAK;
        }
    }

    @Override
    public void setWoodType(LogType logType) {
        switch (logType) {
            case CRIMSON:
                this.setBlockId("minecraft:crimson_door");
                break;
            case WARPED:
                this.setBlockId("minecraft:warped_door");
                break;
            case DARK_OAK:
                this.setBlockId("minecraft:dark_oak_door");
                break;
            case ACACIA:
                this.setBlockId("minecraft:acacia_door");
                break;
            case JUNGLE:
                this.setBlockId("minecraft:jungle_door");
                break;
            case BIRCH:
                this.setBlockId("minecraft:birch_door");
                break;
            case SPRUCE:
                this.setBlockId("minecraft:spruce_door");
                break;
            case OAK:
            default:
                this.setBlockId("minecraft:wooden_door");
        }
    }

    @Override
    public Block getBlock() {
        BlockWoodenDoor block = (BlockWoodenDoor) super.getBlock();
        block.setWoodType(this.getWoodType());
        return block;
    }

}
