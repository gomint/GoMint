/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.world.block;

import io.gomint.world.block.data.SignDirection;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public interface BlockStandingSign extends BlockSign {

    /**
     * Direction of this sign
     *
     * @return sign direction
     */
    SignDirection getSignDirection();

    /**
     * Set the direction of this sign
     *
     * @param direction of this sign
     */
    void setSignDirection(SignDirection direction);

}
