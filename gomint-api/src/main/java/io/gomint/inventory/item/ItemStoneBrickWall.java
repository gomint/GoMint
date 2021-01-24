package io.gomint.inventory.item;

import io.gomint.GoMint;
import io.gomint.world.block.data.StoneBrickSlabType;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public interface ItemStoneBrickWall extends ItemStack<ItemStoneBrickWall> {

    /**
     * Create a new item stack with given class and amount
     *
     * @param amount which is used for the creation
	 * @return freshly generated item
     */
    static ItemStoneBrickWall create(int amount ) {
        return GoMint.instance().createItemStack( ItemStoneBrickWall.class, amount );
    }

    /**
     * Set the type of stone brick
     *
     * @param type of stone brick
     */
    ItemStoneBrickWall type(StoneBrickSlabType type);

    /**
     * Get the type of stone brick
     *
     * @return type of stone brick
     */
    StoneBrickSlabType type();

}
