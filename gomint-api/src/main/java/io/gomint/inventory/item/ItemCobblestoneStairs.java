package io.gomint.inventory.item;

import io.gomint.GoMint;
import io.gomint.world.block.data.CobblestoneType;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public interface ItemCobblestoneStairs extends ItemStack<ItemCobblestoneStairs> {

    /**
     * Create a new item stack with given class and amount
     *
     * @param amount which is used for the creation
	 * @return freshly generated item
     */
    static ItemCobblestoneStairs create( int amount ) {
        return GoMint.instance().createItemStack( ItemCobblestoneStairs.class, amount );
    }

    /**
     * Set the type of cobble stone
     *
     * @param type of cobble stone
     */
    ItemCobblestoneStairs type(CobblestoneType type);

    /**
     * Get the type of cobble stone
     *
     * @return type of cobble stone
     */
    CobblestoneType type();

}
