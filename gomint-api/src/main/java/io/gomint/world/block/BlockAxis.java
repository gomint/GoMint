/*
 * Copyright (c) 2018 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.world.block;

import io.gomint.world.block.data.Axis;

public interface BlockAxis {

    /**
     * Set the axis of the log
     *
     * @param axis of the log
     */
    void setAxis( Axis axis );

    /**
     * Get the axis of this log
     *
     * @return axis of the log
     */
    Axis getAxis();

}
