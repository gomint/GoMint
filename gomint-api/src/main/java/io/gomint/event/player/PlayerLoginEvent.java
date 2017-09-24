package io.gomint.event.player;

import io.gomint.entity.Player;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author geNAZt
 * @version 1.0
 *
 * This event is fired when the login stage begins, way before any data will be sent to the client. If you cancel
 * this event to kick someone the player will have no impact on performance, chunk loading, etc.
 */
@EqualsAndHashCode( callSuper = false )
@ToString( callSuper = true )
public class PlayerLoginEvent extends CancellablePlayerEvent {

    private String kickMessage;

    public PlayerLoginEvent( Player player ) {
        super( player );
    }

    /**
     * If this event is cancelled you have the chance to set a custom kick
     * message here.
     *
     * @return custom kick message
     */
    public String getKickMessage() {
        return this.kickMessage;
    }

    /**
     * Set the custom kick message. This is only used when the event is cancelled and
     * the player is going to be kicked
     *
     * @param kickMessage The custom kick message
     */
    public void setKickMessage( String kickMessage ) {
        this.kickMessage = kickMessage;
    }

}
