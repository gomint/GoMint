package io.gomint.inventory.item;

import io.gomint.GoMint;

/**
 * @author KingAli
 * @version 1.0
 * @stability 3
 */
public interface ItemAllowBlock extends ItemStack {

    /**
     * Create a new item stack with given class and amount
     *
     * @param amount which is used for the creation
     */
    static ItemAllowBlock create( int amount ) {
        return GoMint.instance().createItemStack( ItemAllowBlock.class, 0 );
    }

}
