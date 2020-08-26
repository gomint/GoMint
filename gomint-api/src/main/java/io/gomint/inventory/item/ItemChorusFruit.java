package io.gomint.inventory.item;

import io.gomint.GoMint;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public interface ItemChorusFruit extends ItemFood {

    /**
     * Create a new item stack with given class and amount
     *
     * @param amount which is used for the creation
     */
    static ItemChorusFruit create( int amount ) {
        return GoMint.instance().createItemStack( ItemChorusFruit.class, amount );
    }

}
