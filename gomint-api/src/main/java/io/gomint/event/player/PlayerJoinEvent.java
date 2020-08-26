package io.gomint.event.player;

import io.gomint.entity.EntityPlayer;

import java.util.Objects;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 *
 * This event gets fired after the inital login stage has been completed and the player is ready to be added to the world
 * to be sent to other players (become visible). If you cancel this event the player will never be spawned but it has loaded
 * world chunks and got all resource pack data.
 */
public class PlayerJoinEvent extends CancellablePlayerEvent {

    private String kickReason;
    private String joinMessage;

    public PlayerJoinEvent( EntityPlayer player, String joinMessage ) {
        super( player );      
        this.joinMessage = joinMessage;
    }

    /**
     * Set the message that will be displayed when the player joins the server
     *
     * @param joinMessage the message to display
     */
    public void setJoinMessage( String joinMessage ) {
        this.joinMessage = joinMessage;
    }

    /**
     * Get the message that will be displayed when the player joins the server
     *
     * @return the message that will be displayed
     */
    public String getJoinMessage() {
        return joinMessage;
    }

    /**
     * Set the reason which will be used to disconnect the player when this event has been cancelled
     *
     * @param kickReason which is used to kick the player
     */
    public void setKickReason( String kickReason ) {
        this.kickReason = kickReason;
    }

    /**
     * Get the reason which the player will get then this event has been cancelled
     *
     * @return reason for kick
     */
    public String getKickReason() {
        return kickReason;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        PlayerJoinEvent that = (PlayerJoinEvent) o;
        return Objects.equals(kickReason, that.kickReason) &&
            Objects.equals(joinMessage, that.joinMessage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), kickReason, joinMessage);
    }

    @Override
    public String toString() {
        return "PlayerJoinEvent{" +
            "kickReason='" + kickReason + '\'' +
            ", joinMessage='" + joinMessage + '\'' +
            '}';
    }

}
