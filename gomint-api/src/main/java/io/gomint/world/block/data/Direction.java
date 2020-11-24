/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.world.block.data;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public enum Direction {

    SOUTH,
    NORTH,
    WEST,
    EAST;

    /**
     * Get the opposite face
     *
     * @return opposite face
     */
    public Direction opposite() {
        switch ( this ) {
            case NORTH:
                return Direction.SOUTH;
            case SOUTH:
                return Direction.NORTH;
            case EAST:
                return Direction.WEST;
            case WEST:
                return Direction.EAST;
            default:
                return null;
        }
    }

    /**
     * Get the block face enum value for this facing value
     * @return block face value (3d) for this facing (2d)
     */
    public Facing toFacing() {
        switch ( this ) {
            case NORTH:
                return Facing.NORTH;
            case EAST:
                return Facing.EAST;
            case WEST:
                return Facing.WEST;
            case SOUTH:
                return Facing.SOUTH;
            default:
                return Facing.NORTH;
        }
    }

}
