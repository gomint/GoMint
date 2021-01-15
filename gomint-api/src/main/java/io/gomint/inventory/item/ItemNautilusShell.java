package io.gomint.inventory.item;

import io.gomint.GoMint;

/**
 * @author Kaooot
 * @version 1.0
 * @stability 3
 */
public interface ItemNautilusShell extends ItemStack<ItemNautilusShell> {

    /**
     * Create a new item stack with given class and amount
     *
     * @param amount which is used for the creation
	 * @return freshly generated item
     */
    static ItemNautilusShell create( int amount ) {
        return GoMint.instance().createItemStack( ItemNautilusShell.class, amount );
    }
}
