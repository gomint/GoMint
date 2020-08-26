package io.gomint.inventory.item;

import io.gomint.GoMint;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public interface ItemDiamondChestplate extends ItemStack {

    /**
     * Create a new item stack with given class and amount
     *
     * @param amount which is used for the creation
     */
    static ItemDiamondChestplate create( int amount ) {
        return GoMint.instance().createItemStack( ItemDiamondChestplate.class, amount );
    }

}
