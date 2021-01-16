package io.gomint.inventory.item;

import io.gomint.GoMint;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 2
 */
public interface ItemRail extends ItemStack<ItemRail> {

    /**
     * Create a new item stack with given class and amount
     *
     * @param amount which is used for the creation
	 * @return freshly generated item
     */
    static ItemRail create( int amount ) {
        return GoMint.instance().createItemStack( ItemRail.class, amount );
    }

}
