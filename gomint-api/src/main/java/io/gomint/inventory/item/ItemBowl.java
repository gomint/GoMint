package io.gomint.inventory.item;

import io.gomint.GoMint;

/**
 * @author geNAZt
 * @version 1.0
 */
public interface ItemBowl extends ItemStack, ItemBurnable {

    /**
     * Create a new item stack with given class and amount
     *
     * @param amount which is used for the creation
     */
    static ItemBowl create( int amount ) {
        return GoMint.instance().createItemStack( ItemBowl.class, amount );
    }

    @Override
    default long getBurnTime() {
        return 10000;
    }

}
