/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemStack;
import io.gomint.inventory.item.ItemWoodenDoor;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.block.helper.ToolPresets;
import io.gomint.world.block.BlockType;
import io.gomint.world.block.BlockWoodenDoor;
import io.gomint.world.block.data.Facing;
import io.gomint.world.block.data.LogType;

import java.util.Collections;
import java.util.List;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:wooden_door", def = true)
@RegisterInfo(sId = "minecraft:spruce_door")
@RegisterInfo(sId = "minecraft:birch_door")
@RegisterInfo(sId = "minecraft:jungle_door")
@RegisterInfo(sId = "minecraft:acacia_door")
@RegisterInfo(sId = "minecraft:dark_oak_door")
@RegisterInfo(sId = "minecraft:warped_door")
@RegisterInfo(sId = "minecraft:crimson_door")
public class WoodenDoor extends Door implements BlockWoodenDoor {

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
        return BlockType.WOODEN_DOOR;
    }

    @Override
    public LogType getWoodType() {
        switch (this.getBlockId()) {
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
    public List<ItemStack> getDrops(ItemStack itemInHand) {
        ItemWoodenDoor item = ItemWoodenDoor.create(1);
        item.setWoodType(this.getWoodType());
        return Collections.singletonList(item);
    }

    @Override
    public void afterPlacement() {
        Block above = this.getSide(Facing.UP);
        WoodenDoor aDoor = above.setBlockType(WoodenDoor.class);
        aDoor.setDirection(this.getDirection());
        aDoor.setTop(true);
        aDoor.setWoodType(this.getWoodType());

        super.afterPlacement();
    }

}
