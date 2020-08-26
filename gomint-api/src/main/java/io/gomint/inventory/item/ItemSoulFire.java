package io.gomint.inventory.item;

import io.gomint.GoMint;

/**
 * @author KingAli
 * @version 1.0
 * @stability 1
 */
public interface ItemSoulFire extends ItemStack {

    /**
     * Create a new item stack with given class and amount
     *
     * @param amount which is used for the creation
     */
    static ItemSoulFire create( int amount ) {
        return GoMint.instance().createItemStack( ItemSoulFire.class, amount );
    }
}
