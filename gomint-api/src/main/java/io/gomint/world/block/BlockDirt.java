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
 */
public interface BlockDirt extends Block {

    enum Type {
        NORMAL,
        COARSE,
    }

    /**
     * Set type of dirt
     *
     * @param type which should be set
     */
    void setDirtType(Type type);

    /**
     * Get the type of dirt
     *
     * @return type of dirt
     */
    Type getDirtType();

}
