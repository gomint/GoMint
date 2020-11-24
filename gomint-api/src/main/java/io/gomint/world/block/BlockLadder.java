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
public interface BlockLadder extends Block {

    /**
     * Set the rotation of the ladder. The attach side defines to which block the ladder should connect
     *
     * @param attachSide of this ladder
     */
    void setAttachSide( Facing attachSide );

    /**
     * Get the side to which this ladder is attached
     *
     * @return attached side of this ladder
     */
    Facing getAttachSide();

}
