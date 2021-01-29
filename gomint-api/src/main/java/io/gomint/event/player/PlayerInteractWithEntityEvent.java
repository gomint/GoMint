package io.gomint.event.player;

import io.gomint.entity.Entity;
import io.gomint.entity.EntityPlayer;

/**
 * @author KCodeYT
 * @version 1.0
 * @stability 3
 */
public class PlayerInteractWithEntityEvent extends CancellablePlayerEvent<PlayerInteractWithEntityEvent> {

    private final Entity<?> entity;

    /**
     * Create a new interaction event
     *
     * @param player which interacted with the entity
     * @param entity the entity with which the player interacted
     */
    public PlayerInteractWithEntityEvent(EntityPlayer player, Entity<?> entity) {
        super(player);
        this.entity = entity;
    }

    /**
     * Get the Entity with which the player interacted
     *
     * @return entity which was interacted
     */
    public Entity<?> interactEntity() {
        return this.entity;
    }

    /**
     * Get the player which is affected by this event.
     * 
     * <b>Be careful! This method used to return the entity interacted with before 2.0</b>
     * 
     * @return the player which is affected by this event
     * @see #player() 
     * @see #interactEntity() 
     */
    @Override
    @Deprecated(since = "2.0")
    public EntityPlayer entity() {
        return super.entity();
    }

}
