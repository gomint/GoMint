package io.gomint.event.player;

import io.gomint.entity.EntityPlayer;

/**
 * @author LucGamesHD
 * @version 1.0
 */
public class PlayerAnimationEvent extends CancellablePlayerEvent {

    private int actionId;

    public PlayerAnimationEvent( EntityPlayer player, int actionId ) {
        super(player);
        this.actionId = actionId;
    }

    public int getActionId() { return actionId; }
}
