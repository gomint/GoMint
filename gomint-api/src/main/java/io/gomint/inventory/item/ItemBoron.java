package io.gomint.inventory.item;

import io.gomint.GoMint;

/**
 * @author Kaooot
 * @version 1.0
 */
public interface ItemBoron extends ItemStack {

    /**
     * Create a item stack with given class and amount
     *
     * @param amount which is used for the creation
     */
    static ItemBoron create( int amount ) {
        return GoMint.instance().createItemStack( ItemBoron.class, amount );
    }
}
