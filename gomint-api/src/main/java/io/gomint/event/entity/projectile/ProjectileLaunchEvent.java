/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.event.entity.projectile;

import io.gomint.entity.projectile.EntityProjectile;
import io.gomint.event.entity.CancellableEntityEvent;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ProjectileLaunchEvent extends CancellableEntityEvent {

    private final Cause cause;

    /**
     * Create a new entity based cancellable event
     *
     * @param entity for which this event is
     * @param cause  why this projectile will get launched
     */
    public ProjectileLaunchEvent( EntityProjectile entity, Cause cause ) {
        super( entity );
        this.cause = cause;
    }

    /**
     * Get the cause why this projectile will get launched
     *
     * @return cause of launching
     */
    public Cause getCause() {
        return this.cause;
    }

    @Override
    public EntityProjectile getEntity() {
        return (EntityProjectile) super.getEntity();
    }

    public enum Cause {

        /**
         * Entity shot bow
         */
        BOW_SHOT,

        /**
         * Throwing a exp bottle
         */
        THROWING_EXP_BOTTLE,

        /**
         * When someone throws the fishing rod
         */
        FISHING_ROD,

        /**
         * Throwing ender pearls
         */
        THROWING_ENDER_PEARL,

    }

}
