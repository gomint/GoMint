/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.player;

/**
 * @author BlackyPaw
 * @version 1.0
 */
public interface PlayerSkin {

    /**
     * Gets the name of the player's skin.
     *
     * @return The name of the player's skin
     */
    String getName();

    /**
     * Gets the raw data of the player's skin.
     *
     * @return The raw data of the player's skin
     */
    byte[] getRawData();

    /**
     * Get cape data
     *
     * @return cape data or null
     */
    byte[] getCapeData();

    /**
     * Get the name of the geometry used
     *
     * @return geometry name
     */
    String getGeometryName();

    /**
     * Data used for geometry of the skin
     *
     * @return geometry data
     */
    byte[] getGeometryData();

}
