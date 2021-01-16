/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.world.biome.component;

import io.gomint.entity.Entity;

/**
 * @author geNAZt
 * @version 1.0
 */
public interface SpawnableEntities {

    /**
     * Check if this component can spawn the entity given
     *
     * @param entityClass which should be spawned
     * @return true when it can be spawned, false otherwise
     */
    boolean canSpawn(Class<? extends Entity<?>> entityClass);

    /**
     * Add a new entity class for spawning
     *
     * @param entityClass which should become spawnable
     */
    SpawnableEntities add(Class<? extends Entity<?>> entityClass);

    /**
     * Remove a entity from the spawnable list
     *
     * @param entityClass which should be removed
     */
    SpawnableEntities remove(Class<? extends Entity<?>> entityClass);

}
