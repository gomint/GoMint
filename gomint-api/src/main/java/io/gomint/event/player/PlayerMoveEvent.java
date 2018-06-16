package io.gomint.event.player;

import io.gomint.entity.EntityPlayer;
import io.gomint.math.Location;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author geNAZt
 * @version 1.0
 */
@EqualsAndHashCode( callSuper = true )
@ToString( callSuper = true )
public class PlayerMoveEvent extends CancellablePlayerEvent {

    private final Location from;
    private Location to;

    /**
     * Construct a new player movement event for the given player. The from location
     * can't be changed. If you change the to location the player gets teleported to where
     * you set it.
     *
     * @param player The player which wants to move
     * @param from   The location from where it wants to move
     * @param to     The location to which it wants to move
     */
    public PlayerMoveEvent( EntityPlayer player, Location from, Location to ) {
        super( player );

        this.from = from;
        this.to = to;
    }

    /**
     * Get the from location of this event
     *
     * @return the from location
     */
    public Location getFrom() {
        return this.from;
    }

    /**
     * The location where the player wants to travel to
     *
     * @return the to location
     */
    public Location getTo() {
        return this.to;
    }

    /**
     * You can set the to location to teleport the player. If you cancel the event this will be overwritten
     * by the from value.
     *
     * @param to the new to location
     */
    public void setTo( Location to ) {
        this.to = to;
    }

}
