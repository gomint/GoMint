/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.event.entity.projectile;

import io.gomint.entity.projectile.EntityProjectile;
import io.gomint.event.CancellableEvent;
import io.gomint.world.block.Block;

import java.util.Set;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public class ProjectileHitBlocksEvent extends CancellableEvent<ProjectileHitBlocksEvent> {

    private final Set<Block> blocks;
    private final EntityProjectile<?> projectile;

    /**
     * Create a new entity based cancellable event
     *
     * @param blocks     which got hit by the projectile
     * @param projectile which hit the entity
     */
    public ProjectileHitBlocksEvent( Set<Block> blocks, EntityProjectile<?> projectile ) {
        this.blocks = blocks;
        this.projectile = projectile;
    }

    /**
     * Get the projectile which hit the entity
     *
     * @return projectile which hit the entity
     */
    public EntityProjectile<?> projectile() {
        return this.projectile;
    }

    /**
     * Get the blocks which got hit by the projectile
     *
     * @return blocks which got hit
     */
    public Set<Block> blocks() {
        return this.blocks;
    }

}
