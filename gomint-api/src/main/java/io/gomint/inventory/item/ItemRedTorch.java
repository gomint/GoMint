package io.gomint.inventory.item;

import io.gomint.GoMint;

/**
 * @author Kaooot
 * @version 1.0
 * @stability 1
 */
public interface ItemRedTorch extends ItemStack {

    /**
     * Create a new item stack with given class and amount
     *
     * @param amount which is used for the creation
     */
    static ItemRedTorch create( int amount ) {
        return GoMint.instance().createItemStack( ItemRedTorch.class, amount );
    }
}
