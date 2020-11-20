package io.gomint.inventory.item;

import io.gomint.world.block.data.BlockColor;

import java.awt.Color;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public interface ItemColoredArmor extends ItemStack {

    /**
     * Get the color of this item
     *
     * @return color of this item
     */
    Color getColor();

    /**
     * Set the color of this item
     *
     * @param color which should be used to color this item
     */
    void setColor( Color color );

    /**
     * Set the color based on the dye type used
     *
     * @param dyeColor which should be used to calculate the color
     */
    void setColor( BlockColor dyeColor );

}
