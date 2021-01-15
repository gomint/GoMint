package io.gomint.inventory.item;

import io.gomint.GoMint;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public interface ItemLapisLazuliOre extends ItemStack<ItemLapisLazuliOre> {

    /**
     * Create a new item stack with given class and amount
     *
     * @param amount which is used for the creation
	 * @return freshly generated item
     */
    static ItemLapisLazuliOre create( int amount ) {
        return GoMint.instance().createItemStack( ItemLapisLazuliOre.class, amount );
    }

}
