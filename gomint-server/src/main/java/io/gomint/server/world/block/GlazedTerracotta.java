/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemStack;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.block.helper.ToolPresets;
import io.gomint.server.world.block.state.BlockfaceBlockState;
import io.gomint.world.block.BlockGlazedTerracotta;
import io.gomint.world.block.BlockType;
import io.gomint.world.block.data.BlockColor;
import io.gomint.world.block.data.Facing;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:white_glazed_terracotta", def = true )
@RegisterInfo( sId = "minecraft:orange_glazed_terracotta" )
@RegisterInfo( sId = "minecraft:magenta_glazed_terracotta" )
@RegisterInfo( sId = "minecraft:light_blue_glazed_terracotta" )
@RegisterInfo( sId = "minecraft:yellow_glazed_terracotta" )
@RegisterInfo( sId = "minecraft:lime_glazed_terracotta" )
@RegisterInfo( sId = "minecraft:pink_glazed_terracotta" )
@RegisterInfo( sId = "minecraft:gray_glazed_terracotta" )
@RegisterInfo( sId = "minecraft:silver_glazed_terracotta" )
@RegisterInfo( sId = "minecraft:cyan_glazed_terracotta" )
@RegisterInfo( sId = "minecraft:purple_glazed_terracotta" )
@RegisterInfo( sId = "minecraft:blue_glazed_terracotta" )
@RegisterInfo( sId = "minecraft:brown_glazed_terracotta" )
@RegisterInfo( sId = "minecraft:green_glazed_terracotta" )
@RegisterInfo( sId = "minecraft:red_glazed_terracotta" )
@RegisterInfo( sId = "minecraft:black_glazed_terracotta" )
public class GlazedTerracotta extends Block implements BlockGlazedTerracotta {

    private static final BlockfaceBlockState FACING = new BlockfaceBlockState( () -> new String[]{"facing_direction"} );

    @Override
    public long breakTime() {
        return 2100;
    }

    @Override
    public float blastResistance() {
        return 7.0f;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public Class<? extends ItemStack<?>>[] toolInterfaces() {
        return ToolPresets.PICKAXE;
    }

    @Override
    public BlockType blockType() {
        return BlockType.GLAZED_TERRACOTTA;
    }

    @Override
    public BlockGlazedTerracotta color(BlockColor color ) {
        switch ( color ) {
            case WHITE:
                this.blockId( "minecraft:white_glazed_terracotta" );
                break;
            case ORANGE:
                this.blockId( "minecraft:orange_glazed_terracotta" );
                break;
            case MAGENTA:
                this.blockId( "minecraft:magenta_glazed_terracotta" );
                break;
            case LIGHT_BLUE:
                this.blockId( "minecraft:light_blue_glazed_terracotta" );
                break;
            case YELLOW:
                this.blockId( "minecraft:yellow_glazed_terracotta" );
                break;
            case LIME:
                this.blockId( "minecraft:lime_glazed_terracotta" );
                break;
            case PINK:
                this.blockId( "minecraft:pink_glazed_terracotta" );
                break;
            case GRAY:
                this.blockId( "minecraft:gray_glazed_terracotta" );
                break;
            case LIGHT_GRAY:
                this.blockId( "minecraft:silver_glazed_terracotta" );
                break;
            case CYAN:
                this.blockId( "minecraft:cyan_glazed_terracotta" );
                break;
            case PURPLE:
                this.blockId( "minecraft:purple_glazed_terracotta" );
                break;
            case BLUE:
                this.blockId( "minecraft:blue_glazed_terracotta" );
                break;
            case BROWN:
                this.blockId( "minecraft:brown_glazed_terracotta" );
                break;
            case GREEN:
                this.blockId( "minecraft:green_glazed_terracotta" );
                break;
            case RED:
                this.blockId( "minecraft:red_glazed_terracotta" );
                break;
            case BLACK:
                this.blockId( "minecraft:black_glazed_terracotta" );
                break;
        }

        return this;
    }

    @Override
    public BlockColor color() {
        switch ( this.blockId() ) {
            case "minecraft:white_glazed_terracotta":
                return BlockColor.WHITE;
            case "minecraft:orange_glazed_terracotta":
                return BlockColor.ORANGE;
            case "minecraft:magenta_glazed_terracotta":
                return BlockColor.MAGENTA;
            case "minecraft:light_blue_glazed_terracotta":
                return BlockColor.LIGHT_BLUE;
            case "minecraft:yellow_glazed_terracotta":
                return BlockColor.YELLOW;
            case "minecraft:lime_glazed_terracotta":
                return BlockColor.LIME;
            case "minecraft:pink_glazed_terracotta":
                return BlockColor.PINK;
            case "minecraft:gray_glazed_terracotta":
                return BlockColor.GRAY;
            case "minecraft:silver_glazed_terracotta":
                return BlockColor.LIGHT_GRAY;
            case "minecraft:cyan_glazed_terracotta":
                return BlockColor.CYAN;
            case "minecraft:purple_glazed_terracotta":
                return BlockColor.PURPLE;
            case "minecraft:blue_glazed_terracotta":
                return BlockColor.BLUE;
            case "minecraft:brown_glazed_terracotta":
                return BlockColor.BROWN;
            case "minecraft:green_glazed_terracotta":
                return BlockColor.GREEN;
            case "minecraft:red_glazed_terracotta":
                return BlockColor.RED;
            case "minecraft:black_glazed_terracotta":
                return BlockColor.BLACK;
        }

        return null;
    }

    @Override
    public BlockGlazedTerracotta facing(Facing facing ) {
        FACING.state(this,  facing );
        return this;
    }

    @Override
    public Facing facing() {
        return FACING.state(this);
    }

}
