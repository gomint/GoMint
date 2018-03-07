package io.gomint.event.player;

import io.gomint.entity.EntityPlayer;

/**
 * @author LucGamesHD
 * @version 1.0
 */
public class PlayerAnimationEvent extends CancellablePlayerEvent {

    private Animation animation;

    public PlayerAnimationEvent( EntityPlayer player, Animation animation ) {
        super( player );
        this.animation = animation;
    }

    public Animation getActionId() { return animation; }

    public enum Animation {

        SWING

    }
}
