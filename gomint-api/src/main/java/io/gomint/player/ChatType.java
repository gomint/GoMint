/*
 * Copyright (c) 2018 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.player;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public enum ChatType {

    /**
     * Displays the message in the chat window
     */
    NORMAL,

    /**
     * Show a 2 line message right above the hotbar
     */
    POPUP,

    /**
     * Tip messages are shown above popup messages. The client does color them in pink
     */
    TIP,

    /**
     * System messages are displayed above the hotbar like popup, but there is only one line
     */
    SYSTEM

}
