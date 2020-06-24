package io.gomint.inventory.item;

import io.gomint.GoMint;

/**
 * @author KingAli
 * @version 1.0
 */
public interface ItemPolishedBlackStoneBricks extends ItemStack {

    /**
     * Create a new item stack with given class and amount
     *
     * @param amount which is used for the creation
     */
    static ItemPolishedBlackStoneBricks create( int amount ) {
        return GoMint.instance().createItemStack( ItemPolishedBlackStoneBricks.class, amount );
    }
}
