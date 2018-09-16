/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemStack;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.block.helper.ToolPresets;
import io.gomint.world.block.BlockType;
import io.gomint.world.block.BlockWoodenDoor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 64, itemId = 324 )
@RegisterInfo( id = 193, itemId = 427 )
@RegisterInfo( id = 194, itemId = 428 )
@RegisterInfo( id = 195, itemId = 429 )
@RegisterInfo( id = 196, itemId = 430 )
@RegisterInfo( id = 197, itemId = 431 )
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
    public BlockType getType() {
        return BlockType.WOODEN_DOOR;
    }

    @Override
    public WoodType getWoodType() {
        switch ( this.getBlockId() ) {
            case "minecraft:dark_oak_door":
                return WoodType.DARK_OAK;
            case "minecraft:acacia_door":
                return WoodType.ACACIA;
            case "minecraft:jungle_door":
                return WoodType.JUNGLE;
            case "minecraft:birch_door":
                return WoodType.BIRCH;
            case "minecraft:spruce_door":
                return WoodType.SPRUCE;
            case "minecraft:wooden_door":
            default:
                return WoodType.OAK;
        }
    }

    @Override
    public void setWoodType( WoodType woodType ) {
        switch ( woodType ) {
            case DARK_OAK:
                this.setBlockId( "minecraft:dark_oak_door" );
                break;
            case ACACIA:
                this.setBlockId( "minecraft:acacia_door" );
                break;
            case JUNGLE:
                this.setBlockId( "minecraft:jungle_door" );
                break;
            case BIRCH:
                this.setBlockId( "minecraft:birch_door" );
                break;
            case SPRUCE:
                this.setBlockId( "minecraft:spruce_door" );
                break;
            case OAK:
            default:
                this.setBlockId( "minecraft:wooden_door" );
        }

        this.updateBlock();
    }

    @Override
    public List<ItemStack> getDrops( ItemStack itemInHand ) {
        int itemId;
        switch ( this.getBlockId() ) {
            case "minecraft:dark_oak_door":
                itemId = 431;
                break;
            case "minecraft:acacia_door":
                itemId = 430;
                break;
            case "minecraft:jungle_door":
                itemId = 429;
                break;
            case "minecraft:birch_door":
                itemId = 428;
                break;
            case "minecraft:spruce_door":
                itemId = 427;
                break;
            case "minecraft:wooden_door":
            default:
                itemId = 324;
        }

        return new ArrayList<ItemStack>() {{
            add( world.getServer().getItems().create( itemId, (short) 0, (byte) 1, null ) );
        }};
    }

}
