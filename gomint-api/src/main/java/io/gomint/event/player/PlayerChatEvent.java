package io.gomint.event.player;

import io.gomint.entity.EntityPlayer;

import java.util.List;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public class PlayerChatEvent extends CancellablePlayerEvent {

    private String text;
    private List<EntityPlayer> recipients;
    private String sender;

    /**
     * Create a new chat event
     *
     * @param player which chatted
     * @param text   which has been sent
     */
    public PlayerChatEvent( EntityPlayer player, String sender, String text, List<EntityPlayer> recipients ) {
        super( player );
        this.text = text;
        this.recipients = recipients;
        this.sender = sender;
    }

    /**
     * Get the text which has been sent
     *
     * @return text of this chat event
     */
    public String getText() {
        return this.text;
    }

    /**
     * Set the text of this chat event
     *
     * @param text to display as a result of this chat event
     */
    public void setText( String text ) {
        this.text = text;
    }

    /**
     * Get a list of players which will see this chat
     *
     * @return list of players which will see this
     */
    public List<EntityPlayer> getRecipients() {
        return this.recipients;
    }

    /**
     * Name of the sender which will be passed to the client for display
     *
     * @return sender of the message
     */
    public String getSender() {
        return this.sender;
    }

    /**
     * Set the name of the sender
     *
     * @param sender which will be used to display in the client
     */
    public void setSender( String sender ) {
        this.sender = sender;
    }

}
