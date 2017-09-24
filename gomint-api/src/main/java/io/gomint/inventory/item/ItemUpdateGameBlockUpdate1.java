package io.gomint.inventory.item;

import io.gomint.GoMint;

/**
 * @author geNAZt
 * @version 1.0
 */
public interface ItemUpdateGameBlockUpdate1 extends ItemStack {

    /**
     * Create a new item stack with given class and amount
     *
     * @param amount which is used for the creation
     */
    static ItemUpdateGameBlockUpdate1 create( int amount ) {
        return GoMint.instance().createItemStack( ItemUpdateGameBlockUpdate1.class, amount );
    }

}