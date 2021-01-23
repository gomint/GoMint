package io.gomint.inventory.item;

import io.gomint.GoMint;
import io.gomint.world.block.data.StoneBrickType;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public interface ItemStoneBrick extends ItemStack<ItemStoneBrick> {

    /**
     * Create a new item stack with given class and amount
     *
     * @param amount which is used for the creation
	 * @return freshly generated item
     */
    static ItemStoneBrick create( int amount ) {
        return GoMint.instance().createItemStack( ItemStoneBrick.class, amount );
    }

    /**
     * Set the type of stone brick
     *
     * @param type of stone brick
     */
    ItemStoneBrick type(StoneBrickType type);

    /**
     * Get the type of stone brick
     *
     * @return type of stone brick
     */
    StoneBrickType type();

}
