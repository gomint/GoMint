package io.gomint.inventory.item;

import io.gomint.GoMint;

/**
 * @author geNAZt
 * @version 1.0
 */
public interface ItemLog extends ItemStack {

    /**
     * Create a new item stack with given class and amount
     *
     * @param amount which is used for the creation
     */
    static ItemLog create( int amount ) {
        return GoMint.instance().createItemStack( ItemLog.class, amount );
    }

    /**
     * Set the type of log
     *
     * @param type of log
     */
    void setLogType( Type type );

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
    void setLogDirection( Direction direction );

    /**
     * Get the direction of this log
     *
     * @return direction of the log
     */
    Direction getLogDirection();

    enum Type {
        OAK,
        SPRUCE,
        BIRCH,
        JUNGLE
    }

    enum Direction {
        UP_DOWN,
        EAST_WEST,
        NORTH_SOUTH,
        BARK
    }

}
