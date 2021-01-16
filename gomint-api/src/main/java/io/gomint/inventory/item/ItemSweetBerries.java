package io.gomint.inventory.item;

import io.gomint.GoMint;

/**
 * @author KingAli
 * @version 1.0
 * @stability 3
 */
public interface ItemSweetBerries extends ItemStack<ItemSweetBerries>, ItemFood<ItemSweetBerries> {

    /**
     * Create a new item stack with given class and amount
     *
     * @param amount which is used for the creation
     */
    static ItemSweetBerries create(int amount ) {
        return GoMint.instance().createItemStack( ItemSweetBerries.class, amount );
    }
}
