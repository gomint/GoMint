package io.gomint.inventory.item;

import io.gomint.GoMint;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public interface ItemMobSpawner extends ItemStack<ItemMobSpawner> {

    /**
     * Create a new item stack with given class and amount
     *
     * @param amount which is used for the creation
	 * @return freshly generated item
     */
    static ItemMobSpawner create( int amount ) {
        return GoMint.instance().createItemStack( ItemMobSpawner.class, amount );
    }

}
