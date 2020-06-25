package io.gomint.inventory.item;

import io.gomint.GoMint;

/**
 * @author KingAli
 * @version 1.0
 */
public interface ItemSoulLantern extends ItemStack {

    /**
     * Create a new item stack with given class and amount
     *
     * @param amount which is used for the creation
     */
    static ItemSoulLantern create( int amount ) {
        return GoMint.instance().createItemStack( ItemSoulLantern.class, amount );
    }
}
