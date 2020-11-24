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
 * @stability 2
 */
public interface BlockTallGrass extends Block {

    enum Type {
        GRASS,
        FERN,
        SNOW,
    }

    /**
     * Set the grass type of this tall grass block
     *
     * @param type of this block
     */
    void setGrassType( Type type );

    /**
     * Get the type of this tall grass block
     *
     * @return type of this block
     */
    Type getGrassType();

}
