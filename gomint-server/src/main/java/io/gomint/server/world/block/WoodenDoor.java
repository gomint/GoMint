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
            case (byte) 197:
                return WoodType.DARK_OAK;
            case (byte) 196:
                return WoodType.ACACIA;
            case (byte) 195:
                return WoodType.JUNGLE;
            case (byte) 194:
                return WoodType.BIRCH;
            case (byte) 193:
                return WoodType.SPRUCE;
            case 64:
            default:
                return WoodType.OAK;
        }
    }

    @Override
    public void setWoodType( WoodType woodType ) {
        switch ( woodType ) {
            case DARK_OAK:
                this.setBlockId( (byte) 197 );
                break;
            case ACACIA:
                this.setBlockId( (byte) 196 );
                break;
            case JUNGLE:
                this.setBlockId( (byte) 195 );
                break;
            case BIRCH:
                this.setBlockId( (byte) 194 );
                break;
            case SPRUCE:
                this.setBlockId( (byte) 193 );
                break;
            case OAK:
            default:
                this.setBlockId( (byte) 64 );
        }

        this.updateBlock();
    }

    @Override
    public List<ItemStack> getDrops( ItemStack itemInHand ) {
        int itemId;
        switch ( this.getBlockId() ) {
            case (byte) 197:
                itemId = 431;
                break;
            case (byte) 196:
                itemId = 430;
                break;
            case (byte) 195:
                itemId = 429;
                break;
            case (byte) 194:
                itemId = 428;
                break;
            case (byte) 193:
                itemId = 427;
                break;
            case 64:
            default:
                itemId = 324;
        }

        return new ArrayList<ItemStack>() {{
            add( world.getServer().getItems().create( itemId, (short) 0, (byte) 1, null ) );
        }};
    }

}
