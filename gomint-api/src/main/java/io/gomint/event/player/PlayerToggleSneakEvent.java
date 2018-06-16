package io.gomint.event.player;

import io.gomint.entity.EntityPlayer;

/**
 * @author LucGamesHD
 * @version 1.0
 */
public class PlayerToggleSneakEvent extends CancellablePlayerEvent {

    private final boolean newStatus;

    public PlayerToggleSneakEvent(EntityPlayer player, boolean newStatus) {
        super(player);
        this.newStatus = newStatus;
    }

    /**
     * Get the status the client wants to set.
     *
     * @return true when the client wants to start sneaking, false otherwise
     */
    public boolean getNewStatus() {
        return this.newStatus;
    }

}
