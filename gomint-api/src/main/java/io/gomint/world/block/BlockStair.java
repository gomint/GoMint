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
public interface BlockStair extends BlockDirection {

    /**
     * Get if the base of the stair on the top or not
     *
     * @return true if base is on top, false otherwise
     */
    boolean isTop();

    /**
     * Set if base of the stair is on top or not
     *
     * @param top true if base is on top, false otherwise
     */
    void setTop(boolean top);

}
