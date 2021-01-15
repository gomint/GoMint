package io.gomint.inventory.item;

import io.gomint.GoMint;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 2
 */
public interface ItemMap extends ItemStack<ItemMap> {

    /**
     * Create a new item stack with given class and amount
     *
     * @param amount which is used for the creation
	 * @return freshly generated item
     */
    static ItemMap create( int amount ) {	
        return GoMint.instance().createItemStack( ItemMap.class, amount );
    }

}
