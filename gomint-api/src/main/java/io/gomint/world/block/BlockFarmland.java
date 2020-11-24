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
public interface BlockFarmland extends Block {

    /**
     * Get moisture level of this block
     *
     * @return moisture level
     */
    float getMoisture();

    /**
     * Set moisture level of this block
     *
     * @param moisture level of this block
     */
    void setMoisture(float moisture);

}
