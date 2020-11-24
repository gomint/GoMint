/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.world.block;

import io.gomint.world.block.data.LanternType;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 2
 */
public interface BlockLantern extends Block {

    /**
     * Set type of lantern
     *
     * @param type of lantern
     */
    void setLanternType(LanternType type);

    /**
     * Get lantern type
     *
     * @return lantern type
     */
    LanternType getLanternType();

    /**
     * Does this lantern hang or stand?
     *
     * @return true when hanging, false otherwise
     */
    boolean isHanging();

    /**
     * Should this lantern hang or stand?
     *
     * @param hanging true when hanging, false otherwise
     */
    void setHanging(boolean hanging);

}
