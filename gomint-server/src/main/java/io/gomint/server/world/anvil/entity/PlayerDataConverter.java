/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world.anvil.entity;

import io.gomint.server.entity.EntityPlayer;

/**
 * @author geNAZt
 * @version 1.0
 */
public interface PlayerDataConverter {

    /**
     * Read data from the world and set it into the player if needed
     *
     * @param player which should be read from the world data
     */
    void read( EntityPlayer player );

    /**
     * Write the entity to the world
     *
     * @param player which should be written to the world
     */
    void write( EntityPlayer player );

}
