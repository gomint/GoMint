package io.gomint.inventory.item;

import io.gomint.GoMint;

/**
 * @author KingAli
 * @version 1.0
 */
public interface ItemCrimsonRoots extends ItemStack {

    /**
     * Create a new item stack with given class and amount
     *
     * @param amount which is used for the creation
     */
    static ItemCrimsonRoots create( int amount ) {
        return GoMint.instance().createItemStack( ItemCrimsonRoots.class, amount );
    }
}
