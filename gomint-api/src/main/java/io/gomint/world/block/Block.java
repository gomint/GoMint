/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.world.block;

import io.gomint.math.AxisAlignedBB;
import io.gomint.math.Location;

/**
 * @author geNAZt
 * @author BlackyPaw
 * @version 1.0
 */
public interface Block {

    /**
     * Gets the ID of the block.
     *
     * @return The ID of the block
     */
    int getBlockId();

    /**
     * Gets the block's data value (0 - 15)
     *
     * @return The block's data value
     */
    byte getBlockData();

    /**
     * Get the level of skylight this block has
     *
     * @return The skylight data of this block
     */
    byte getSkyLightLevel();

    /**
     * get the level of block light
     *
     * @return The block light data
     */
    byte getBlockLightLevel();

    /**
     * Does this block let sky light shine trough?
     *
     * @return true when it does, false when it does not
     */
    boolean isTransparent();

    /**
     * Is this block solid?
     *
     * @return true when its solid, false when not
     */
    boolean isSolid();

    /**
     * Get the current location of this block
     *
     * @return Location of this block
     */
    Location getLocation();

    /**
     * Set the type of this block to another material
     *
     * @param <T>       block generic type
     * @param blockType the new material of this block
     * @param data      optional data for the block
     */
    <T extends Block> T setType( Class<T> blockType, byte data );

    /**
     * Can a bounding box pass through this block?
     *
     * @return if a bounding box can pass though or not
     */
    boolean canPassThrough();

    /**
     * Get the bounding box of this block
     *
     * @return the bounding box of this block
     */
    AxisAlignedBB getBoundingBox();

    /**
     * Describes how slippery a block is
     *
     * @return the amount of blocks something can slip on this block
     */
    float getFrictionFactor();

    Block getSide( int face );

}
