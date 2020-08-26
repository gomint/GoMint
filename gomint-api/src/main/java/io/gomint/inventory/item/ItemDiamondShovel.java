package io.gomint.inventory.item;

import io.gomint.GoMint;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public interface ItemDiamondShovel extends ItemShovel {

    /**
     * Create a new item stack with given class and amount
     *
     * @param amount which is used for the creation
     */
    static ItemDiamondShovel create( int amount ) {
        return GoMint.instance().createItemStack( ItemDiamondShovel.class, amount );
    }

}
