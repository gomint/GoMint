package io.gomint.inventory.item;

import io.gomint.GoMint;
import io.gomint.world.block.data.BlockColor;

/**
 * @author geNAZt
 * @version 1.0
 */
public interface ItemBed extends ItemStack {

    /**
     * Create a new item stack with given class and amount
     *
     * @param amount which is used for the creation
     */
    static ItemBed create( int amount ) {
        return GoMint.instance().createItemStack( ItemBed.class, amount );
    }

    /**
     * Get the color of this bed
     *
     * @return color of this bed
     */
    BlockColor getColor();

    /**
     * Set the color of this bed
     *
     * @param color which this bed should be
     */
    void setColor( BlockColor color );

}
