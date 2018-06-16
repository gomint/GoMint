/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.event.player;

import io.gomint.entity.EntityPlayer;
import io.gomint.event.Event;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author geNAZt
 * @version 1.0
 */
@EqualsAndHashCode( callSuper = false )
@ToString( callSuper = true )
public class PlayerEvent extends Event {

    private final EntityPlayer player;

    public PlayerEvent( EntityPlayer player ) {
        this.player = player;
    }

    /**
     * Get the player which is affected by this event
     *
     * @return the player which is affected by this event
     */
    public EntityPlayer getPlayer() {
        return this.player;
    }

}
