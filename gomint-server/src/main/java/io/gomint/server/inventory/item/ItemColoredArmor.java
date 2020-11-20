package io.gomint.server.inventory.item;

import io.gomint.world.block.data.BlockColor;

import java.awt.*;

/**
 * @author geNAZt
 * @version 1.0
 */
public abstract class ItemColoredArmor extends ItemArmor implements io.gomint.inventory.item.ItemColoredArmor {

    private static final String NBT_CUSTOM_COLOR_TAG = "customColor";

    @Override
    public Color getColor() {
        // Do we have a NBT tag?
        if (this.getNbtData() == null) {
            return null;
        }

        // Do we have color data?
        int rgb = this.getNbtData().getInteger(NBT_CUSTOM_COLOR_TAG, -1);
        if (rgb == -1) {
            return null;
        }


        return new Color(rgb);
    }

    @Override
    public void setColor(Color color) {
        int rgb = color.getRed() << 16 | color.getGreen() << 8 | color.getBlue();
        this.getOrCreateNBT().addValue(NBT_CUSTOM_COLOR_TAG, rgb);
    }

    @Override
    public void setColor(BlockColor dyeColor) {
        switch (dyeColor) {
            case BLACK:
                this.setColor(hex2Rgb("#1D1D21"));
                return;
            case RED:
                this.setColor(hex2Rgb("#B02E26"));
                return;
            case GREEN:
                this.setColor(hex2Rgb("#5E7C16"));
                return;
            case BROWN:
                this.setColor(hex2Rgb("#835432"));
                return;
            case BLUE:
                this.setColor(hex2Rgb("#3C44AA"));
                return;
            case PURPLE:
                this.setColor(hex2Rgb("#8932B8"));
                return;
            case CYAN:
                this.setColor(hex2Rgb("#169C9C"));
                return;
            case LIGHT_GRAY:
                this.setColor(hex2Rgb("#9D9D97"));
                return;
            case GRAY:
                this.setColor(hex2Rgb("#474F52"));
                return;
            case PINK:
                this.setColor(hex2Rgb("#F38BAA"));
                return;
            case LIME:
                this.setColor(hex2Rgb("#80C71F"));
                return;
            case YELLOW:
                this.setColor(hex2Rgb("#FED83D"));
                return;
            case LIGHT_BLUE:
                this.setColor(hex2Rgb("#3AB3DA"));
                return;
            case MAGENTA:
                this.setColor(hex2Rgb("#C74EBD"));
                return;
            case ORANGE:
                this.setColor(hex2Rgb("#F9801D"));
                return;
            case WHITE:
                this.setColor(hex2Rgb("#F9FFFE"));
        }
    }

    private Color hex2Rgb(String colorStr) {
        return new Color(
            Integer.valueOf(colorStr.substring(1, 3), 16),
            Integer.valueOf(colorStr.substring(3, 5), 16),
            Integer.valueOf(colorStr.substring(5, 7), 16));
    }

}
