package io.gomint.event.player;

import io.gomint.entity.EntityPlayer;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 *
 * This event is fired when a client has disconnected. Its called before entity cleanup so you can access all data loaded
 * associated with the entity or alter data in it before its persisted.
 */
public class PlayerQuitEvent extends PlayerEvent {

    private String quitMessage;

    /**
     * Construct a quit event with the player who disconnected
     *
     * @param player The player which disconnected
     */
    public PlayerQuitEvent( EntityPlayer player, String quitMessage ) {
        super( player );
        this.quitMessage = quitMessage;
    }

    /**
     * Set the message that will be displayed when the player leaves the server
     *
     * @param quitMessage the message to display
     */
    public void setQuitMessage( String quitMessage ) {
        this.quitMessage = quitMessage;
    }

    /**
     * Get the message that will be displayed when the player leaves the server
     *
     * @return the message that will be displayed
     */
    public String getQuitMessage() {
        return quitMessage;
    }

}
