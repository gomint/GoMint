package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.Block;
import io.gomint.world.block.BlockWoodenDoor;
import io.gomint.world.block.data.LogType;

import java.time.Duration;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:wooden_door", def = true)
@RegisterInfo(sId = "minecraft:acacia_door")
@RegisterInfo(sId = "minecraft:birch_door")
@RegisterInfo(sId = "minecraft:dark_oak_door")
@RegisterInfo(sId = "minecraft:jungle_door")
@RegisterInfo(sId = "minecraft:warped_door")
@RegisterInfo(sId = "minecraft:crimson_door")
@RegisterInfo(sId = "minecraft:spruce_door")
public class ItemWoodenDoor extends ItemStack< io.gomint.inventory.item.ItemWoodenDoor> implements io.gomint.inventory.item.ItemWoodenDoor {

    @Override
    public Duration burnTime() {
        return Duration.ofMillis(10000);
    }

    @Override
    public ItemType itemType() {
        return ItemType.WOODEN_DOOR;
    }

    @Override
    public LogType type() {
        switch (this.material()) {
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
    public ItemWoodenDoor type(LogType logType) {
        switch (logType) {
            case CRIMSON:
                this.blockId("minecraft:crimson_door");
                break;
            case WARPED:
                this.blockId("minecraft:warped_door");
                break;
            case DARK_OAK:
                this.blockId("minecraft:dark_oak_door");
                break;
            case ACACIA:
                this.blockId("minecraft:acacia_door");
                break;
            case JUNGLE:
                this.blockId("minecraft:jungle_door");
                break;
            case BIRCH:
                this.blockId("minecraft:birch_door");
                break;
            case SPRUCE:
                this.blockId("minecraft:spruce_door");
                break;
            case OAK:
            default:
                this.blockId("minecraft:wooden_door");
        }

        return this;
    }

    @Override
    public Block block() {
        BlockWoodenDoor block = (BlockWoodenDoor) super.block();
        block.type(this.type());
        return block;
    }

}
