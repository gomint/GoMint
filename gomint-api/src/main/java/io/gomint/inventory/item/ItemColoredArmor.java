package io.gomint.inventory.item;

import io.gomint.world.block.data.BlockColor;

import java.awt.Color;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public interface ItemColoredArmor<I> extends ItemStack<I> {

    /**
     * Get the color of this item
     *
     * @return color of this item
     */
    Color color();

    /**
     * Set the color of this item
     *
     * @param color which should be used to color this item
     */
    I color(Color color );

    /**
     * Set the color based on the dye type used
     *
     * @param dyeColor which should be used to calculate the color
     */
    I color(BlockColor dyeColor );

}
