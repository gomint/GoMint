package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemStack;
import io.gomint.world.block.data.BlockColor;

import java.awt.*;

/**
 * @author geNAZt
 * @version 1.0
 */
public abstract class ItemColoredArmor<I extends ItemStack<I>> extends ItemArmor<I> implements io.gomint.inventory.item.ItemColoredArmor<I> {

    private static final String NBT_CUSTOM_COLOR_TAG = "customColor";

    @Override
    public Color color() {
        // Do we have a NBT tag?
        if (this.nbtData() == null) {
            return null;
        }

        // Do we have color data?
        int rgb = this.nbtData().getInteger(NBT_CUSTOM_COLOR_TAG, -1);
        if (rgb == -1) {
            return null;
        }


        return new Color(rgb);
    }

    @Override
    public I color(Color color) {
        int rgb = color.getRed() << 16 | color.getGreen() << 8 | color.getBlue();
        this.nbt().addValue(NBT_CUSTOM_COLOR_TAG, rgb);
        return (I) this;
    }

    @Override
    public I color(BlockColor dyeColor) {
        switch (dyeColor) {
            case BLACK:
                this.color(hex2Rgb("#1D1D21"));
                return (I) this;
            case RED:
                this.color(hex2Rgb("#B02E26"));
                return (I) this;
            case GREEN:
                this.color(hex2Rgb("#5E7C16"));
                return (I) this;
            case BROWN:
                this.color(hex2Rgb("#835432"));
                return (I) this;
            case BLUE:
                this.color(hex2Rgb("#3C44AA"));
                return (I) this;
            case PURPLE:
                this.color(hex2Rgb("#8932B8"));
                return (I) this;
            case CYAN:
                this.color(hex2Rgb("#169C9C"));
                return (I) this;
            case LIGHT_GRAY:
                this.color(hex2Rgb("#9D9D97"));
                return (I) this;
            case GRAY:
                this.color(hex2Rgb("#474F52"));
                return (I) this;
            case PINK:
                this.color(hex2Rgb("#F38BAA"));
                return (I) this;
            case LIME:
                this.color(hex2Rgb("#80C71F"));
                return (I) this;
            case YELLOW:
                this.color(hex2Rgb("#FED83D"));
                return (I) this;
            case LIGHT_BLUE:
                this.color(hex2Rgb("#3AB3DA"));
                return (I) this;
            case MAGENTA:
                this.color(hex2Rgb("#C74EBD"));
                return (I) this;
            case ORANGE:
                this.color(hex2Rgb("#F9801D"));
                return (I) this;
            case WHITE:
                this.color(hex2Rgb("#F9FFFE"));
        }

        return (I) this;
    }

    private Color hex2Rgb(String colorStr) {
        return new Color(
            Integer.valueOf(colorStr.substring(1, 3), 16),
            Integer.valueOf(colorStr.substring(3, 5), 16),
            Integer.valueOf(colorStr.substring(5, 7), 16));
    }

}
