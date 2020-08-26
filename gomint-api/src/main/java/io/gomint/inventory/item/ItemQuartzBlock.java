package io.gomint.inventory.item;

import io.gomint.GoMint;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 2
 */
public interface ItemQuartzBlock extends ItemStack {

    /**
     * Create a new item stack with given class and amount
     *
     * @param amount which is used for the creation
     */
    static ItemQuartzBlock create( int amount ) {
        return GoMint.instance().createItemStack( ItemQuartzBlock.class, amount );
    }

}
