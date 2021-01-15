package io.gomint.inventory.item;

import io.gomint.GoMint;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public interface ItemHopper extends ItemStack<ItemHopper> {

    /**
     * Create a new item stack with given class and amount
     *
     * @param amount which is used for the creation
	 * @return freshly generated item
     */
    static ItemHopper create( int amount ) {
        return GoMint.instance().createItemStack( ItemHopper.class, amount );
    }

}
