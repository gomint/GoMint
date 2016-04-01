/*
 * Copyright (c) 2015, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint;

import io.gomint.entity.Player;

import java.util.Collection;

/**
 * @author BlackyPaw
 * @author geNAZt
 * @version 1.0
 */
public interface GoMint {

    /**
     * Get the server's message of the day (MOTD)
     *
     * @return The server's current MOTD
     */
    String getMotd();

    /**
     * Sets the server's message of the day (MOTD)
     *
     * @param motd The MOTD to be set
     */
    void setMotd( String motd );

    /**
     * Get's a collection of all online players on the server
     *
     * @return the collection
     */
    Collection<Player> getPlayers();

}
