/*
 * Copyright (c) 2018 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.world.block.data;

/**
 * @author geNAZt
 * @version 1.0
 */
public enum Facing {

    // CHECKSTYLE:OFF
    DOWN,
    UP,
    EAST,
    WEST,
    NORTH,
    SOUTH;
    // CHECKSTYLE:ON

    /**
     * Get the opposite of the current facing
     *
     * @return opposite facing site
     */
    public Facing opposite() {
        switch ( this ) {
            case DOWN:
                return UP;
            case UP:
                return DOWN;
            case EAST:
                return WEST;
            case WEST:
                return EAST;
            case NORTH:
                return SOUTH;
            case SOUTH:
                return NORTH;
        }

        return NORTH;
    }

    /**
     * Get the face enum value for this block facing value
     * @return face value (2d) for this block facing (3d)
     */
    public Direction toDirection() {
        switch ( this ) {
            case NORTH:
                return Direction.NORTH;
            case EAST:
                return Direction.EAST;
            case WEST:
                return Direction.WEST;
            case SOUTH:
                return Direction.SOUTH;
            default:
                return Direction.NORTH;
        }
    }

}
