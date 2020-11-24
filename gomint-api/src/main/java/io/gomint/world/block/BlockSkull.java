/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.world.block;

import io.gomint.world.block.data.SkullType;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 2
 */
public interface BlockSkull extends Block, BlockDirection {

    /**
     * Get type of skull
     *
     * @return type of skull
     */
    SkullType getSkullType();

    /**
     * Set type of skull
     *
     * @param type of skull to set
     */
    void setSkullType(SkullType type);

}
