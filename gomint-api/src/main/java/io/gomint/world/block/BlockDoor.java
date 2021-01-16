/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.world.block;

import io.gomint.world.block.data.HingeSide;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public interface BlockDoor<B> extends BlockDirection<B> {

    /**
     * Is the door part top or bottom?
     *
     * @return true when its the top part, false when not
     */
    boolean top();

    /**
     * Is the door open or closed?
     *
     * @return true when the door is open, false when not
     */
    boolean open();

    /**
     * Open the door or close it
     *
     * @param open or close the door
     * @return block for chaining
     */
    B open(boolean open);

    /**
     * Open or close a door. The target state depends on the {@link #open()} state
     */
    B toggle();

    /**
     * Set the side where the hinge is on
     *
     * @param side of the hinge
     * @return block for chaining
     */
    B hingeSide(HingeSide side);

    /**
     * Get the side where the hinge is on
     *
     * @return side of the hinge
     */
    HingeSide hingeSide();

}
