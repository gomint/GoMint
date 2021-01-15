/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.event.entity;

import io.gomint.entity.Entity;
import io.gomint.event.Event;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public class EntityDespawnEvent extends Event {

    private final Entity<?> entity;

    /**
     * Create a new event for announcing a entity despawn
     *
     * @param entity for which this event is
     */
    public EntityDespawnEvent( Entity<?> entity ) {
        this.entity = entity;
    }

    /**
     * Get the player which is affected by this event
     *
     * @return the player which is affected by this event
     */
    public Entity<?> entity() {
        return this.entity;
    }

}
