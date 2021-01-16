package io.gomint.inventory.item;

import io.gomint.GoMint;
import io.gomint.world.block.data.StoneType;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public interface ItemStoneSlab extends ItemSlab<ItemStoneSlab> {

    /**
     * Create a new item stack with given class and amount
     *
     * @param amount which is used for the creation
	 * @return freshly generated item
     */
    static ItemStoneSlab create( int amount ) {
        return GoMint.instance().createItemStack( ItemStoneSlab.class, amount );
    }

    /**
     * Get the type of stone this slab has
     *
     * @return type of stone
     */
    StoneType type();

    /**
     * Set the type of stone for this slab
     *
     * @param type which this slab should have
     */
    ItemStoneSlab type(StoneType type);

}
