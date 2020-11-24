/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.inventory.item;

import io.gomint.world.block.data.Axis;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public interface ItemAxis extends ItemStack {

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
