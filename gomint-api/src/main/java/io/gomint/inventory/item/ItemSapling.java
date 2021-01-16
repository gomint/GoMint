package io.gomint.inventory.item;

import io.gomint.GoMint;
import io.gomint.world.block.data.LogType;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public interface ItemSapling extends ItemStack<ItemSapling>, ItemBurnable {

    /**
     * Create a new item stack with given class and amount
     *
     * @param amount which is used for the creation
	 * @return freshly generated item
     */
    static ItemSapling create( int amount ) {
        return GoMint.instance().createItemStack( ItemSapling.class, amount );
    }

    /**
     * Set the type of sapling
     *
     * @param type of sapling
     */
    ItemSapling type(LogType type );

    /**
     * Get the type of this sapling
     *
     * @return type of sapling
     */
    LogType type();

}
