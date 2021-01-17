/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.data.BlockColor;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = ItemGlazedTerracotta.PURPLE)
@RegisterInfo(sId = ItemGlazedTerracotta.WHITE, def = true)
@RegisterInfo(sId = ItemGlazedTerracotta.ORANGE)
@RegisterInfo(sId = ItemGlazedTerracotta.MAGENTA)
@RegisterInfo(sId = ItemGlazedTerracotta.LIGHT_BLUE)
@RegisterInfo(sId = ItemGlazedTerracotta.YELLOW)
@RegisterInfo(sId = ItemGlazedTerracotta.LIME)
@RegisterInfo(sId = ItemGlazedTerracotta.PINK)
@RegisterInfo(sId = ItemGlazedTerracotta.GRAY)
@RegisterInfo(sId = ItemGlazedTerracotta.LIGHT_GRAY)
@RegisterInfo(sId = ItemGlazedTerracotta.CYAN)
@RegisterInfo(sId = ItemGlazedTerracotta.BLUE)
@RegisterInfo(sId = ItemGlazedTerracotta.BROWN)
@RegisterInfo(sId = ItemGlazedTerracotta.GREEN)
@RegisterInfo(sId = ItemGlazedTerracotta.RED)
@RegisterInfo(sId = ItemGlazedTerracotta.BLACK)
public class ItemGlazedTerracotta extends ItemStack< io.gomint.inventory.item.ItemGlazedTerracotta> implements io.gomint.inventory.item.ItemGlazedTerracotta {

    public static final String PURPLE = "minecraft:purple_glazed_terracotta";
    public static final String WHITE = "minecraft:white_glazed_terracotta";
    public static final String ORANGE = "minecraft:orange_glazed_terracotta";
    public static final String MAGENTA = "minecraft:magenta_glazed_terracotta";
    public static final String LIGHT_BLUE = "minecraft:light_blue_glazed_terracotta";
    public static final String YELLOW = "minecraft:yellow_glazed_terracotta";
    public static final String LIME = "minecraft:lime_glazed_terracotta";
    public static final String PINK = "minecraft:pink_glazed_terracotta";
    public static final String GRAY = "minecraft:gray_glazed_terracotta";
    public static final String LIGHT_GRAY = "minecraft:silver_glazed_terracotta";
    public static final String CYAN = "minecraft:cyan_glazed_terracotta";
    public static final String BLUE = "minecraft:blue_glazed_terracotta";
    public static final String BROWN = "minecraft:brown_glazed_terracotta";
    public static final String GREEN = "minecraft:green_glazed_terracotta";
    public static final String RED = "minecraft:red_glazed_terracotta";
    public static final String BLACK = "minecraft:black_glazed_terracotta";

    @Override
    public ItemType itemType() {
        return ItemType.GLAZED_TERRACOTTA;
    }

    @Override
    public ItemGlazedTerracotta color(BlockColor type) {
        switch (type) {
            case BLACK:
                this.material(ItemGlazedTerracotta.BLACK);
                break;
            case RED:
                this.material(ItemGlazedTerracotta.RED);
                break;
            case GREEN:
                this.material(ItemGlazedTerracotta.GREEN);
                break;
            case BROWN:
                this.material(ItemGlazedTerracotta.BROWN);
                break;
            case BLUE:
                this.material(ItemGlazedTerracotta.BLUE);
                break;
            case PURPLE:
                this.material(ItemGlazedTerracotta.PURPLE);
                break;
            case CYAN:
                this.material(ItemGlazedTerracotta.CYAN);
                break;
            case LIGHT_GRAY:
                this.material(ItemGlazedTerracotta.LIGHT_GRAY);
                break;
            case GRAY:
                this.material(ItemGlazedTerracotta.GRAY);
                break;
            case PINK:
                this.material(ItemGlazedTerracotta.PINK);
                break;
            case LIME:
                this.material(ItemGlazedTerracotta.LIME);
                break;
            case YELLOW:
                this.material(ItemGlazedTerracotta.YELLOW);
                break;
            case LIGHT_BLUE:
                this.material(ItemGlazedTerracotta.LIGHT_BLUE);
                break;
            case MAGENTA:
                this.material(ItemGlazedTerracotta.MAGENTA);
                break;
            case ORANGE:
                this.material(ItemGlazedTerracotta.ORANGE);
                break;
            case WHITE:
                this.material(ItemGlazedTerracotta.WHITE);
                break;
        }

        return this;
    }

    @Override
    public BlockColor color() {
        String data = this.material();
        switch (data) {
            case ItemGlazedTerracotta.BLACK:
                return BlockColor.BLACK;
            case ItemGlazedTerracotta.RED:
                return BlockColor.RED;
            case ItemGlazedTerracotta.GREEN:
                return BlockColor.GREEN;
            case ItemGlazedTerracotta.BROWN:
                return BlockColor.BROWN;
            case ItemGlazedTerracotta.BLUE:
                return BlockColor.BLUE;
            case ItemGlazedTerracotta.PURPLE:
                return BlockColor.PURPLE;
            case ItemGlazedTerracotta.CYAN:
                return BlockColor.CYAN;
            case ItemGlazedTerracotta.LIGHT_GRAY:
                return BlockColor.LIGHT_GRAY;
            case ItemGlazedTerracotta.GRAY:
                return BlockColor.GRAY;
            case ItemGlazedTerracotta.PINK:
                return BlockColor.PINK;
            case ItemGlazedTerracotta.LIME:
                return BlockColor.LIME;
            case ItemGlazedTerracotta.YELLOW:
                return BlockColor.YELLOW;
            case ItemGlazedTerracotta.LIGHT_BLUE:
                return BlockColor.LIGHT_BLUE;
            case ItemGlazedTerracotta.MAGENTA:
                return BlockColor.MAGENTA;
            case ItemGlazedTerracotta.ORANGE:
                return BlockColor.ORANGE;
            case ItemGlazedTerracotta.WHITE:
                return BlockColor.WHITE;
        }

        return BlockColor.BLACK;
    }

}
