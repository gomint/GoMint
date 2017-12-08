/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.world.block;

/**
 * @author geNAZt
 * @version 1.0
 */
public interface BlockBlockOfQuartz extends Block {

    /**
     * Get the variant of this quartz block
     *
     * @return variant of this block
     */
    Variant getVariant();

    /**
     * Set the variant of this block
     *
     * @param variant which should be used
     */
    void setVariant( Variant variant );

    public enum Variant {
        NORMAL,
        CHISELED,
        VERTICAL_PILLAR,
        NORTH_SOUTH_PILLAR,
        EAST_WEST_PILLAR
    }

}
