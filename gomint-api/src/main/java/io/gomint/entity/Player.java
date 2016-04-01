/*
 * Copyright (c) 2015, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.entity;

import io.gomint.command.CommandSender;

import java.util.UUID;

/**
 * @author BlackyPaw
 * @author Digot
 */
public interface Player extends Entity, CommandSender {
    /**
     * Gets the name of the player. It is NOT globally unique since the
     * player can change it inside the Client. Yet it is unique for all
     * players on the same server.
     *
     * @return The name player's name
     */
    String getName();

	/**
	 * Gets the player's UUID. It has yet to be researched how unique this
	 * one is as it may be specified by the player during the login sequence.
	 *
	 * @return The player's UUID.
	 */
	UUID getUUID();


    
    //void sendPopup(String message);
    //void sendPopup(String message, String submessage);
    //void sendTip(String message);
    
}
