package io.gomint.inventory.item;

import java.awt.Color;

/**
 * @author geNAZt
 * @version 1.0
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

}
