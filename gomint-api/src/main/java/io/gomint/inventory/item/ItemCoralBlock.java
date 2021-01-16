package io.gomint.inventory.item;

import io.gomint.GoMint;

/**
 * @author Kaooot
 * @version 1.0
 * @stability 2
 */
public interface ItemCoralBlock extends ItemStack<ItemCoralBlock> {

    /**
     * Creates a new item stack with given class and amount
     *
     * @param amount which is used for the creation
	 * @return freshly generated item
     */
    static ItemCoralBlock create( int amount ) {	
        return GoMint.instance().createItemStack( ItemCoralBlock.class, amount );
    }
}
