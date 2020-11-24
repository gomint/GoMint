/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.world.block.data;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
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

    public static Facing[] HORIZONTAL = new Facing[]{Facing.NORTH, Facing.EAST, Facing.SOUTH, Facing.WEST};

    public static Facing getRandom() {
        return values()[ThreadLocalRandom.current().nextInt(values().length)];
    }

    /**
     * Get the opposite of the current facing
     *
     * @return opposite facing site
     */
    public Facing opposite() {
        switch (this) {
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

        throw new IllegalStateException("unable to get opposite for " + this);
    }

    /**
     * Get the face enum value for this block facing value
     *
     * @return face value (2d) for this block facing (3d)
     */
    public Direction toDirection() {
        switch (this) {
            case NORTH:
                return Direction.NORTH;
            case EAST:
                return Direction.EAST;
            case WEST:
                return Direction.WEST;
            case SOUTH:
                return Direction.SOUTH;
            default:
                throw new IllegalStateException("unable to get direction for " + this);
        }
    }

    public Facing rotateClockWiseOnY() {
        switch (this) {
            case NORTH:
                return EAST;

            case EAST:
                return SOUTH;

            case SOUTH:
                return WEST;

            case WEST:
                return NORTH;

            default:
                throw new IllegalStateException("unable to rotate clockwise on y for " + this);
        }
    }

    public Facing rotateCounterClockWiseOnY() {
        switch (this) {
            case NORTH:
                return WEST;

            case EAST:
                return NORTH;

            case SOUTH:
                return EAST;

            case WEST:
                return SOUTH;

            default:
                throw new IllegalStateException("unable to rotate counter clockwise on y for " + this);
        }
    }

}
