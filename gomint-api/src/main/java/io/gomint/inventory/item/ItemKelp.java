package io.gomint.inventory.item;

import io.gomint.GoMint;

/**
 * @author Kaooot
 * @version 1.0
 * @stability 3
 */
public interface ItemKelp extends ItemStack<ItemKelp> {

    /**
     * Create a new item stack with given class and amount
     *
     * @param amount which is used for the creation
	 * @return freshly generated item
     */
    static ItemKelp create( int amount ) {
        return GoMint.instance().createItemStack( ItemKelp.class, amount );
    }
}
