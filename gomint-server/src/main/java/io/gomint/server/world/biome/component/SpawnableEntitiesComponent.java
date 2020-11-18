/*
 * Copyright (c) 2018 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world.biome.component;

import io.gomint.entity.Entity;
import io.gomint.world.biome.component.SpawnableEntities;

import java.util.Set;

/**
 * @author geNAZt
 * @version 1.0
 */
public class SpawnableEntitiesComponent implements SpawnableEntities, Component {

    private final Set<Class<? extends Entity>> spawnables;

    /**
     * Create a new spawnable component
     *
     * @param spawnables which are allowed per default
     */
    public SpawnableEntitiesComponent(Set<Class<? extends Entity>> spawnables) {
        this.spawnables = spawnables;
    }

    @Override
    public boolean canSpawn(Class<? extends Entity> entityClass) {
        return this.spawnables.contains(entityClass);
    }

    @Override
    public void add(Class<? extends Entity> entityClass) {
        this.spawnables.add(entityClass);
    }

    @Override
    public void remove(Class<? extends Entity> entityClass) {
        this.spawnables.remove(entityClass);
    }

}
