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
public class EntitySpawnEvent extends CancellableEntityEvent {

    /**
     * Create a new entity based cancellable event
     *
     * @param entity for which this event is
     */
    public EntitySpawnEvent( Entity entity ) {
        super( entity );
    }

}
