/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.entity;

import io.gomint.entity.Entity;
import io.gomint.server.entity.metadata.MetadataContainer;
import io.gomint.server.world.WorldAdapter;

/**
 * @author KCodeYT
 * @version 1.0
 */
public abstract class EntityAgeable<E extends Entity<E>> extends EntityLiving<E> implements io.gomint.entity.EntityAgeable<E> {

    /**
     * Constructs a new EntityLiving
     *
     * @param type  The type of the Entity
     * @param world The world in which this entity is in
     */
    protected EntityAgeable(EntityType type, WorldAdapter world) {
        super(type, world);
    }

    @Override
    public boolean baby() {
        return this.metadataContainer.getDataFlag( MetadataContainer.DATA_INDEX, EntityFlag.BABY );
    }

    @Override
    public E baby(boolean value ) {
        this.metadataContainer.setDataFlag( MetadataContainer.DATA_INDEX, EntityFlag.BABY, value );
        return (E) this;
    }

}
