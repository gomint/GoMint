package io.gomint.inventory.item;

import io.gomint.GoMint;

/**
 * @author geNAZt
 * @version 1.0
 */
public interface ItemDarkOakDoor extends ItemStack {

    /**
     * Create a new item stack with given class and amount
     *
     * @param amount which is used for the creation
     */
    static ItemDarkOakDoor create( int amount ) {
        return GoMint.instance().createItemStack( ItemDarkOakDoor.class, amount );
    }

}