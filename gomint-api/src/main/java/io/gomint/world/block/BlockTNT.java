/*
 * Copyright (c) 2018 Gomint team
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
public interface BlockTNT extends Block {

    /**
     * Prime this tnt
     *
     * @param untilExplodeSeconds seconds until the tnt explodes
     */
    void prime( float untilExplodeSeconds );

}
