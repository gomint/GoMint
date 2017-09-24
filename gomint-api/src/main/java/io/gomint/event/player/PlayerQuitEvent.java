package io.gomint.event.player;

import io.gomint.entity.Player;

/**
 * @author geNAZt
 * @version 1.0
 *
 * This event is fired when a client has disconnected. Its called before entity cleanup so you can access all data loaded
 * associated with the entity or alter data in it before its persisted.
 */
public class PlayerQuitEvent extends PlayerEvent {

    /**
     * Construct a quit event with the player who disconnected
     *
     * @param player The player which disconnected
     */
    public PlayerQuitEvent( Player player ) {
        super( player );
    }

}
