/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.event.player;

import io.gomint.entity.EntityPlayer;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 *
 * This event gets called once the entity has been cleaned up and despawned. There is NO data left in the server except
 * the object copy in this event.
 */
public class PlayerCleanedupEvent extends PlayerEvent {

    /**
     * Construct a quit event with the player who disconnected
     *
     * @param player The player which disconnected
     */
    public PlayerCleanedupEvent( EntityPlayer player ) {
        super( player );
    }

}
