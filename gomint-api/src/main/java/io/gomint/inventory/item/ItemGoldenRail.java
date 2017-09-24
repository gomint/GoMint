package io.gomint.inventory.item;

import io.gomint.GoMint;

/**
 * @author geNAZt
 * @version 1.0
 */
public interface ItemGoldenRail extends ItemStack {

    /**
     * Create a new item stack with given class and amount
     *
     * @param amount which is used for the creation
     */
    static ItemGoldenRail create( int amount ) {
        return GoMint.instance().createItemStack( ItemGoldenRail.class, amount );
    }

}