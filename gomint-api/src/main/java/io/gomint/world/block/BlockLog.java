/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.world.block;

/**
 * @author geNAZt
 * @version 1.0
 */
public interface BlockLog extends Block {

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
