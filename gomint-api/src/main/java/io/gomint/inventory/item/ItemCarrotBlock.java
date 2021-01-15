package io.gomint.inventory.item;

import io.gomint.GoMint;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public interface ItemCarrotBlock extends ItemStack<ItemCarrotBlock> {

    /**
     * Create a new item stack with given class and amount
     *
     * @param amount which is used for the creation
	 * @return freshly generated item
     */
    static ItemCarrotBlock create( int amount ) {
        return GoMint.instance().createItemStack( ItemCarrotBlock.class, amount );
    }

}
