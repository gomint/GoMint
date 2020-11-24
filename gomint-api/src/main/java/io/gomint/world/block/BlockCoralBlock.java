/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.world.block;

/**
 * @author Kaooot
 * @version 1.0
 * @stability 1
 */
public interface BlockCoralBlock extends Block {

    /**
     * Get coral type
     *
     * @return coral type
     */
    CoralType getCoralType();

    /**
     * Set the coral type
     *
     * @param coralType which should be used in this block
     */
    void setCoralType( CoralType coralType );

    enum CoralType {
        TUBE,
        BRAIN,
        BUBBLE,
        FIRE,
        HORN,
        DEAD_TUBE,
        DEAD_BRAIN,
        DEAD_BUBBLE,
        DEAD_FIRE,
        DEAD_HORN;
    }

}
