/*
 * Copyright (c) 2020 GoMint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.command;

import io.gomint.player.ChatType;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public interface CommandSender {

    /**
     * Send a message to the client, this uses the normal {@link ChatType} enum.
     *
     * @param message which should be send to the client
     */
    void sendMessage( String message );

    /**
     * Send a message with a given type to the client
     *
     * @param message which should be send
     * @param type    of the message
     */
    void sendMessage( ChatType type, String... message );

    /**
     * Check if player has a specific permission
     *
     * @param permission which should be checked for
     * @return true if the player has this permission, false if not
     */
    boolean hasPermission( String permission );

    /**
     * Check if player has a specific permission
     *
     * @param permission which should be checked for
     * @return true if the player has this permission, defaultValue if not
     */
    boolean hasPermission( String permission, boolean defaultValue );

}
