package io.gomint.inventory.item;

import io.gomint.GoMint;
import io.gomint.world.block.data.SkullType;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public interface ItemSkull extends ItemStack<ItemSkull> {

    /**
     * Create a new item stack with given class and amount
     *
     * @param amount which is used for the creation
	 * @return freshly generated item
     */
    static ItemSkull create( int amount ) {
        return GoMint.instance().createItemStack( ItemSkull.class, amount );
    }

    /**
     * Get type of skull
     *
     * @return type of skull
     */
    SkullType type();

    /**
     * Set type of skull
     *
     * @param type of skull to set
     */
    ItemSkull type(SkullType type);

}
