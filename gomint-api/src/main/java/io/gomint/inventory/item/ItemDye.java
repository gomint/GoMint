package io.gomint.inventory.item;

import io.gomint.GoMint;
import io.gomint.world.block.data.BlockColor;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public interface ItemDye extends ItemStack<ItemDye> {

    /**
     * Create a new item stack with given class and amount
     *
     * @param amount which is used for the creation
     */
    static ItemDye create(int amount) {
        return GoMint.instance().createItemStack(ItemDye.class, amount);
    }

    /**
     * Set the color of this dye
     *
     * @param color of dye
     * @return item for chaining
     */
    ItemDye color(BlockColor color);

    /**
     * Get the color of dye
     *
     * @return color of dye
     */
    BlockColor color();

}
