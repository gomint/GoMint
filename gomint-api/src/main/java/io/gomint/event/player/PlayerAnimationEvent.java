package io.gomint.event.player;

import io.gomint.entity.EntityPlayer;

public class PlayerAnimationEvent extends CancellablePlayerEvent {

    private Animation animation;

    public PlayerAnimationEvent( EntityPlayer player, Animation animation ) {
        super(player);
        this.animation = animation;
    }

    public Animation getAnimation() { return animation; }

    public enum Animation {

        SWING

    }
}
