/*
 * Copyright (c) 2018 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.world.block;

/**
 * @author geNAZt
 * @version 1.0
 */
public interface BlockDoor extends BlockDirection {

    /**
     * Is the door part top or bottom?
     *
     * @return true when its the top part, false when not
     */
    boolean isTop();

    /**
     * Is the door open or closed?
     *
     * @return true when the door is open, false when not
     */
    boolean isOpen();

    /**
     * Open or close a door. The target state depends on the {@link #isOpen()} state
     */
    void toggle();

}
