package io.gomint.entity;

/**
 * @author geNAZt
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
