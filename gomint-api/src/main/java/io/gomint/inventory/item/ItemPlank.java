package io.gomint.inventory.item;

import io.gomint.GoMint;
import io.gomint.world.block.data.LogType;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 2
 */
public interface ItemPlank extends ItemStack, ItemBurnable {

    /**
     * Create a new item stack with given class and amount
     *
     * @param amount which is used for the creation
     */
    static ItemPlank create(int amount ) {
        return GoMint.instance().createItemStack( ItemPlank.class, amount );
    }

    /**
     * Get plank type
     *
     * @return plank type
     */
    LogType getPlankType();

    /**
     * Set the plank type
     *
     * @param logType which should be used in this block
     */
    void setPlankType( LogType logType);

}
