package io.gomint.event.player;

import io.gomint.entity.EntityPlayer;

/**
 * @author geNAZt
 * @version 1.0
 */
public class PlayerCraftingEvent extends CancellablePlayerEvent {

    public PlayerCraftingEvent( EntityPlayer player ) {
        super( player );
    }

}
