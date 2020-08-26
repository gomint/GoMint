package io.gomint.inventory.item;

import io.gomint.GoMint;

/**
 * @author Kaooot
 * @version 1.0
 * @stability 1
 */
public interface ItemBlueTorch extends ItemStack {

    /**
     * Create a new item stack with given class and amount
     *
     * @param amount which is used for the creation
     */
    static ItemBlueTorch create( int amount ) {
        return GoMint.instance().createItemStack( ItemBlueTorch.class, amount );
    }
}
