package io.gomint.event.player;

import io.gomint.entity.EntityPlayer;

/**
 * @author lukeeey
 * @version 1.0
 */
public class PlayerSwimEvent extends CancellablePlayerEvent {

    private final boolean newStatus;

    public PlayerSwimEvent(EntityPlayer player, boolean newStatus) {
        super(player);
        this.newStatus = newStatus;
    }

    /**
     * Get the status the client wants to set.
     *
     * @return true when the client wants to start swimming, false otherwise
     */
    public boolean getNewStatus() {
        return this.newStatus;
    }
}
