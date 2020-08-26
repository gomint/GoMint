package io.gomint.inventory.item;

import io.gomint.GoMint;

/**
 * @author Kaooot
 * @version 1.0
 * @stability 2
 */
public interface ItemCoral extends ItemStack {

    /**
     * Creates a new item stack with given class and amount
     *
     * @param amount which is used for the creation
     */
    static ItemCoral create( int amount ) {
        return GoMint.instance().createItemStack( ItemCoral.class, amount );
    }
}
