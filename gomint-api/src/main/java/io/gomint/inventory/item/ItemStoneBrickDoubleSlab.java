package io.gomint.inventory.item;

import io.gomint.GoMint;
import io.gomint.world.block.data.StoneBrickSlabType;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public interface ItemStoneBrickDoubleSlab extends ItemStack<ItemStoneBrickDoubleSlab> {

    /**
     * Create a new item stack with given class and amount
     *
     * @param amount which is used for the creation
	 * @return freshly generated item
     */
    static ItemStoneBrickDoubleSlab create(int amount ) {
        return GoMint.instance().createItemStack( ItemStoneBrickDoubleSlab.class, amount );
    }

    /**
     * Set the type of stone brick
     *
     * @param type of stone brick
     */
    ItemStoneBrickDoubleSlab type(StoneBrickSlabType type);

    /**
     * Get the type of stone brick
     *
     * @return type of stone brick
     */
    StoneBrickSlabType type();

}
