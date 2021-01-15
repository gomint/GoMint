package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.data.BlockColor;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = ItemDye.BLACK, id = 393, def = true)
@RegisterInfo(sId = ItemDye.RED, id = 394)
@RegisterInfo(sId = ItemDye.GREEN, id = 395)
@RegisterInfo(sId = ItemDye.BROWN, id = 396)
@RegisterInfo(sId = ItemDye.BLUE, id = 397)
@RegisterInfo(sId = ItemDye.PURPLE, id = 398)
@RegisterInfo(sId = ItemDye.CYAN, id = 399)
@RegisterInfo(sId = ItemDye.LIGHT_GRAY, id = 400)
@RegisterInfo(sId = ItemDye.GRAY, id = 401)
@RegisterInfo(sId = ItemDye.PINK, id = 402)
@RegisterInfo(sId = ItemDye.LIME, id = 403)
@RegisterInfo(sId = ItemDye.YELLOW, id = 404)
@RegisterInfo(sId = ItemDye.LIGHT_BLUE, id = 405)
@RegisterInfo(sId = ItemDye.MAGENTA, id = 406)
@RegisterInfo(sId = ItemDye.ORANGE, id = 407)
@RegisterInfo(sId = ItemDye.WHITE, id = 408)
@RegisterInfo(sId = "minecraft:dye", id = 612) // Only for vanilla conversion
public class ItemDye extends ItemStack< io.gomint.inventory.item.ItemDye> implements io.gomint.inventory.item.ItemDye {

    public static final String BLACK = "minecraft:black_dye";
    public static final String RED = "minecraft:red_dye";
    public static final String GREEN = "minecraft:green_dye";
    public static final String BROWN = "minecraft:brown_dye";
    public static final String BLUE = "minecraft:blue_dye";
    public static final String PURPLE = "minecraft:purple_dye";
    public static final String CYAN = "minecraft:cyan_dye";
    public static final String LIGHT_GRAY = "minecraft:light_gray_dye";
    public static final String GRAY = "minecraft:gray_dye";
    public static final String PINK = "minecraft:pink_dye";
    public static final String LIME = "minecraft:lime_dye";
    public static final String YELLOW = "minecraft:yellow_dye";
    public static final String LIGHT_BLUE = "minecraft:light_blue_dye";
    public static final String MAGENTA = "minecraft:magenta_dye";
    public static final String ORANGE = "minecraft:orange_dye";
    public static final String WHITE = "minecraft:white_dye";

    @Override
    public String material() {
        if (super.material().equals("minecraft:dye")) {
            return ItemDye.BLACK;
        }

        return super.material();
    }

    @Override
    public ItemType itemType() {
        return ItemType.DYE;
    }

    @Override
    public ItemDye color(BlockColor type) {
        switch (type) {
            case BLACK:
                this.material(ItemDye.BLACK);
                break;
            case RED:
                this.material(ItemDye.RED);
                break;
            case GREEN:
                this.material(ItemDye.GREEN);
                break;
            case BROWN:
                this.material(ItemDye.BROWN);
                break;
            case BLUE:
                this.material(ItemDye.BLUE);
                break;
            case PURPLE:
                this.material(ItemDye.PURPLE);
                break;
            case CYAN:
                this.material(ItemDye.CYAN);
                break;
            case LIGHT_GRAY:
                this.material(ItemDye.LIGHT_GRAY);
                break;
            case GRAY:
                this.material(ItemDye.GRAY);
                break;
            case PINK:
                this.material(ItemDye.PINK);
                break;
            case LIME:
                this.material(ItemDye.LIME);
                break;
            case YELLOW:
                this.material(ItemDye.YELLOW);
                break;
            case LIGHT_BLUE:
                this.material(ItemDye.LIGHT_BLUE);
                break;
            case MAGENTA:
                this.material(ItemDye.MAGENTA);
                break;
            case ORANGE:
                this.material(ItemDye.ORANGE);
                break;
            case WHITE:
                this.material(ItemDye.WHITE);
                break;
        }

        return this;
    }

    @Override
    public BlockColor color() {
        String data = this.material();
        switch (data) {
            case ItemDye.BLACK:
                return BlockColor.BLACK;
            case ItemDye.RED:
                return BlockColor.RED;
            case ItemDye.GREEN:
                return BlockColor.GREEN;
            case ItemDye.BROWN:
                return BlockColor.BROWN;
            case ItemDye.BLUE:
                return BlockColor.BLUE;
            case ItemDye.PURPLE:
                return BlockColor.PURPLE;
            case ItemDye.CYAN:
                return BlockColor.CYAN;
            case ItemDye.LIGHT_GRAY:
                return BlockColor.LIGHT_GRAY;
            case ItemDye.GRAY:
                return BlockColor.GRAY;
            case ItemDye.PINK:
                return BlockColor.PINK;
            case ItemDye.LIME:
                return BlockColor.LIME;
            case ItemDye.YELLOW:
                return BlockColor.YELLOW;
            case ItemDye.LIGHT_BLUE:
                return BlockColor.LIGHT_BLUE;
            case ItemDye.MAGENTA:
                return BlockColor.MAGENTA;
            case ItemDye.ORANGE:
                return BlockColor.ORANGE;
            case ItemDye.WHITE:
                return BlockColor.WHITE;
        }

        return BlockColor.BLACK;
    }

}
