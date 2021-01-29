/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */
package io.gomint.event.player;

import io.gomint.entity.EntityPlayer;

/**
 * Event is called when a player is about to leave a world (through teleportation or after server join).
 * 
 * Event is called in world's thread. Event is called after EntityTeleportEvent.
 * 
 * @author Janmm14
 * @version 2.0
 * @stability 2
 */
public class PlayerWorldLeaveEvent extends SimplePlayerEvent {

    /**
     * Create an event for announcing a player joining a world
     *
     * @param player the player joining the world
     */
    public PlayerWorldLeaveEvent(EntityPlayer player) {
        super(player);
    }

}
