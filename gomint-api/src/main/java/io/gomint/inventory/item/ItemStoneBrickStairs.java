package io.gomint.inventory.item;

import io.gomint.GoMint;
import io.gomint.world.block.data.StoneBrickStairsType;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public interface ItemStoneBrickStairs extends ItemStack<ItemStoneBrickStairs> {

    /**
     * Create a new item stack with given class and amount
     *
     * @param amount which is used for the creation
     * @return freshly generated item
     */
    static ItemStoneBrickStairs create(int amount) {
        return GoMint.instance().createItemStack(ItemStoneBrickStairs.class, amount);
    }

    /**
     * Set the type of stone brick stairs
     *
     * @param type of stone brick stairs
     */
    ItemStoneBrickStairs type(StoneBrickStairsType type);

    /**
     * Get the type of stone brick stairs
     *
     * @return type of stone brick stairs
     */
    StoneBrickStairsType type();

}
