package io.gomint.inventory.item;

import io.gomint.GoMint;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public interface ItemEndCrystal extends ItemStack<ItemEndCrystal> {

    /**
     * Create a new item stack with given class and amount
     *
     * @param amount which is used for the creation
	 * @return freshly generated item
     */
    static ItemEndCrystal create( int amount ) {
        return GoMint.instance().createItemStack( ItemEndCrystal.class, amount );
    }

}
