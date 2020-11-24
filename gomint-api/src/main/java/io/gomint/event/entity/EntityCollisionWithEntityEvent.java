/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.event.entity;

import io.gomint.entity.Entity;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public class EntityCollisionWithEntityEvent extends CancellableEntityEvent {

    private final Entity collidestWith;

    /**
     * Create a new entity based cancellable event
     *
     * @param entity       for which this event is
     * @param collidesWith which collides with the other entity
     */
    public EntityCollisionWithEntityEvent( Entity entity, Entity collidesWith ) {
        super( entity );
        this.collidestWith = collidesWith;
    }

    /**
     * Get the entity which we collide with. You can cancel the event to cancel the collision action (for example
     * get hit by a projectile)
     *
     * @return entity which collides with the target entity
     */
    public Entity getCollidestWith() {
        return this.collidestWith;
    }

}
