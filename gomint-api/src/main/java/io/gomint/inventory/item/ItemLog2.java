package io.gomint.inventory.item;

import io.gomint.GoMint;

/**
 * @author KCodeYT
 * @version 1.0
 */
public interface ItemLog2 extends ItemStack, ItemBurnable {

    /**
     * Create a new item stack with given class and amount
     *
     * @param amount which is used for the creation
     */
    static ItemLog2 create(int amount) {
        return GoMint.instance().createItemStack( ItemLog2.class, amount );
    }

    /**
     * Set the type of log
     *
     * @param type of log
     */
    void setLogType(Type type);

    /**
     * Get the type of this log
     *
     * @return type of log
     */
    Type getLogType();

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

    @Override
    default long getBurnTime() {
        return 15000;
    }

    enum Type {
        ACACIA,
        DARK_OAK
    }

    enum Direction {
        UP_DOWN,
        EAST_WEST,
        NORTH_SOUTH,
        BARK
    }

}
