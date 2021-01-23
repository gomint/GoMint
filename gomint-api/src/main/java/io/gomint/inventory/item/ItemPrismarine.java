package io.gomint.inventory.item;

import io.gomint.GoMint;
import io.gomint.world.block.data.PrismarineType;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public interface ItemPrismarine extends ItemStack<ItemPrismarine> {

    /**
     * Create a new item stack with given class and amount
     *
     * @param amount which is used for the creation
	 * @return freshly generated item
     */
    static ItemPrismarine create( int amount ) {	
        return GoMint.instance().createItemStack( ItemPrismarine.class, amount );
    }

    /**
     * Set the type of prismarine
     *
     * @param type of prismarine
     */
    ItemPrismarine type(PrismarineType type);

    /**
     * Get the type of prismarine
     *
     * @return type of prismarine
     */
    PrismarineType type();

}
