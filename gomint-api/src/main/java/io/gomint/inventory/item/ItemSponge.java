package io.gomint.inventory.item;

import io.gomint.GoMint;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public interface ItemSponge extends ItemStack<ItemSponge> {

    /**
     * Create a new item stack with given class and amount
     *
     * @param amount which is used for the creation
	 * @return freshly generated item
     */
    static ItemSponge create( int amount ) {	
        return GoMint.instance().createItemStack( ItemSponge.class, amount );
    }

}
