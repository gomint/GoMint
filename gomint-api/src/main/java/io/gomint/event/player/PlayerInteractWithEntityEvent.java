package io.gomint.event.player;

import io.gomint.entity.Entity;
import io.gomint.entity.EntityPlayer;
import lombok.ToString;

/**
 * @author KCodeYT
 * @version 1.0
 */
@ToString
public class PlayerInteractWithEntityEvent extends CancellablePlayerEvent {

    private Entity entity;

    /**
     * Create a new interaction event
     *
     * @param player    which interacted with the entity
     * @param entity    the entity with which the player interacted
     */
    public PlayerInteractWithEntityEvent( EntityPlayer player, Entity entity ) {
        super( player );
        this.entity = entity;
    }

    /**
     * Get the Entity with which the player interacted
     *
     * @return entity which was interacted
     */
    public Entity getEntity() {
        return entity;
    }

}
