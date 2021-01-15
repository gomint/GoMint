/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.world.block;

import io.gomint.world.block.data.Axis;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public interface BlockAxis<B> {

    /**
     * Set the axis of the log
     *
     * @param axis of the log
     * @return block for chaining
     */
    B axis(Axis axis);

    /**
     * Get the axis of this log
     *
     * @return axis of the log
     */
    Axis axis();

}
