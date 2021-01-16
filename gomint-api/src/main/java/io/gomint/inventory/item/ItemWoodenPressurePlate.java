package io.gomint.inventory.item;

import io.gomint.GoMint;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 2
 */
public interface ItemWoodenPressurePlate extends ItemStack<ItemWoodenPressurePlate>, ItemBurnable {

    /**
     * Create a new item stack with given class and amount
     *
     * @param amount which is used for the creation
	 * @return freshly generated item
     */
    static ItemWoodenPressurePlate create( int amount ) {
        return GoMint.instance().createItemStack( ItemWoodenPressurePlate.class, amount );
    }

}
