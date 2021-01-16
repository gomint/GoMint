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
 * @stability 3
 */
public interface BlockButton<B> extends Block, BlockFacing<B> {

    /**
     * Get the state of this button
     *
     * @return true when currently pressed, false otherwise
     */
    boolean pressed();

    /**
     * Press the button (it will release after 1 second)
     */
    B press();

}
