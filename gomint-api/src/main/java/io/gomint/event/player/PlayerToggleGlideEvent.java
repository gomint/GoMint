/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.event.player;

import io.gomint.entity.EntityPlayer;

/**
 * @author geNAZt
 * @version 1.0
 */
public class PlayerToggleGlideEvent extends CancellablePlayerEvent {

    private final boolean newStatus;

    public PlayerToggleGlideEvent( EntityPlayer player, boolean newStatus ) {
        super( player );
        this.newStatus = newStatus;
    }

    /**
     * Get the status the client wants to set.
     *
     * @return true when the client wants to start flying, false otherwise
     */
    public boolean getNewStatus() {
        return this.newStatus;
    }

}
