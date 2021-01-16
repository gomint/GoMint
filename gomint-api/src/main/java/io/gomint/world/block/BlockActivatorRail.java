/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.world.block;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 1
 */
public interface BlockActivatorRail extends Block {

    enum Direction {
        NORTH_SOUTH,
        EAST_WEST,
        ASCENDING_EAST,
        ASCENDING_WEST,
        ASCENDING_NORTH,
        ASCENDING_SOUTH;
    }

    /**
     * Direction of the rail
     *
     * @param direction which this rail should be oriented by
     * @return block for chaining
     */
    BlockActivatorRail direction(Direction direction);

    /**
     * Get direction of the rail
     *
     * @return direction of this rail
     */
    Direction direction();

}
