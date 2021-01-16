/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.entity.animal;

import io.gomint.entity.Entity;
import io.gomint.server.entity.EntityLiving;
import io.gomint.server.entity.EntityTags;
import io.gomint.server.entity.EntityType;
import io.gomint.server.world.WorldAdapter;

import java.util.Set;

/**
 * @author geNAZt
 * @version 1.0
 */
public abstract class EntityAnimal<E extends Entity<E>> extends EntityLiving<E> {

    /**
     * Constructs a new EntityLiving
     *
     * @param type  The type of the Entity
     * @param world The world in which this entity is in
     */
    protected EntityAnimal(EntityType type, WorldAdapter world) {
        super(type, world);
    }

    @Override
    public Set<String> tags() {
        return EntityTags.ANIMAL;
    }

}
