package io.gomint.inventory.item;

import io.gomint.GoMint;

/**
 * @author Kaooot
 * @version 1.0
 */
public interface ItemBeryllium extends ItemStack {

    /**
     * Create a new item stack with given class and amount
     *
     * @param amount which is used for the creation
     */
    static ItemBeryllium create( int amount ) {
        return GoMint.instance().createItemStack( ItemBeryllium.class, amount );
    }
}
