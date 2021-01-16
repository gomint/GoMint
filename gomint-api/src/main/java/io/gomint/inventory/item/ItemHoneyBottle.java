package io.gomint.inventory.item;

import io.gomint.GoMint;

/**
 * @author KingAli
 * @version 1.0
 * @stability 3
 */
public interface ItemHoneyBottle extends ItemFood<ItemHoneyBottle> {

    /**
     * Create a new item stack with given class and amount
     *
     * @param amount which is used for the creation
     */

    static ItemHoneyBottle create( int amount ) {
        return GoMint.instance().createItemStack( ItemHoneyBottle.class, amount );
    }

}
