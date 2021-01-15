package io.gomint.inventory.item;

import io.gomint.GoMint;

/**
 * @author KingAli
 * @version 1.0
 * @stability 3
 */
public interface ItemNetheriteSword extends ItemStack<ItemNetheriteSword> {

    /**
     * Create a new item stack with given class and amount
     *
     * @param amount which is used for the creation
	 * @return freshly generated item
     */
    static ItemNetheriteSword create( int amount ) {
        return GoMint.instance().createItemStack( ItemNetheriteSword.class, amount );
    }
}
