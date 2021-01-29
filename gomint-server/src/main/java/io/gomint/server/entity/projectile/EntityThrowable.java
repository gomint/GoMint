/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.entity.projectile;

import io.gomint.entity.Entity;
import io.gomint.server.entity.EntityLiving;
import io.gomint.server.entity.EntityType;
import io.gomint.server.world.WorldAdapter;

/**
 * @author geNAZt
 * @version 1.0
 */
public abstract class EntityThrowable<E extends Entity<E>> extends EntityProjectile<E> {

    /**
     * Construct a new Entity
     *
     * @param shooter of this entity
     * @param type    The type of the Entity
     * @param world   The world in which this entity is in
     */
    protected EntityThrowable(EntityLiving<?> shooter, EntityType type, WorldAdapter world) {
        super(shooter, type, world);

        // Set owning entity
        if (shooter != null) {
            this.metadataContainer.putLong(5, shooter.id());
        }
    }

    @Override
    protected void applyCustomProperties() {
        super.applyCustomProperties();

        // Gravity
        this.gravity = 0.03f;
        this.drag = 0.01f;

        // Set size
        this.size(0.25f, 0.25f);
    }

    @Override
    public boolean critical() {
        return false;
    }

    @Override
    public float damage() {
        return 0;
    }

}
