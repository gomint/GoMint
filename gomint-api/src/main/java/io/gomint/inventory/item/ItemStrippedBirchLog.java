package io.gomint.inventory.item;

import io.gomint.GoMint;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 1
 */
public interface ItemStrippedBirchLog extends ItemStack, ItemBurnable {

    /**
     * Create a new item stack with given class and amount
     *
     * @param amount which is used for the creation
     */
    static ItemStrippedBirchLog create(int amount) {
        return GoMint.instance().createItemStack( ItemStrippedBirchLog.class, amount );
    }

    /**
     * Set the direction of the log
     *
     * @param direction of the log
     */
    void setLogDirection(Direction direction);

    /**
     * Get the direction of this log
     *
     * @return direction of the log
     */
    Direction getLogDirection();

    enum Direction {
        UP_DOWN,
        EAST_WEST,
        NORTH_SOUTH,
        BARK
    }

}
