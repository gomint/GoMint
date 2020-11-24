/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.world.block;

import io.gomint.world.block.data.Facing;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public interface BlockButton extends Block {

    /**
     * Get the state of this button
     *
     * @return true when currently pressed, false otherwise
     */
    boolean isPressed();

    /**
     * Press the button (it will release after 1 second)
     */
    void press();

    /**
     * Get the attached facing which has been used to attach this button to a block
     *
     * @return attached face
     */
    Facing getAttachedFace();

    /**
     * Set the attached facing of this button
     *
     * @param face which is attached to a block
     */
    void setAttachedFace(Facing face);

}
