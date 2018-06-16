package io.gomint.event.player;

import io.gomint.entity.EntityPlayer;

/**
 * @author LucGamesHD
 * @version 1.0
 */
public class PlayerToggleSprintEvent extends CancellablePlayerEvent {

    private final boolean newStatus;

    public PlayerToggleSprintEvent(EntityPlayer player, boolean newStatus) {
        super(player);
        this.newStatus = newStatus;
    }

    /**
     * Get the status the client wants to set.
     *
     * @return true when the client wants to start sprinting, false otherwise
     */
    public boolean getNewStatus() {
        return this.newStatus;
    }
}
