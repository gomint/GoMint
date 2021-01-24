package io.gomint.inventory.item;

import io.gomint.GoMint;
import io.gomint.world.block.data.StoneBrickSlabType;
import io.gomint.world.block.data.StoneBrickType;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public interface ItemStoneBrickSlab extends ItemSlab<ItemStoneBrickSlab> {

    /**
     * Create a new item stack with given class and amount
     *
     * @param amount which is used for the creation
	 * @return freshly generated item
     */
    static ItemStoneBrickSlab create(int amount ) {
        return GoMint.instance().createItemStack( ItemStoneBrickSlab.class, amount );
    }

    /**
     * Set the type of stone brick
     *
     * @param type of stone brick
     */
    ItemStoneBrickSlab type(StoneBrickSlabType type);

    /**
     * Get the type of stone brick
     *
     * @return type of stone brick
     */
    StoneBrickSlabType type();

}
