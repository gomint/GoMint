/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.entity.animal;

import io.gomint.server.entity.Attribute;
import io.gomint.server.entity.EntityType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.WorldAdapter;

@RegisterInfo(sId = "minecraft:skeleton_horse")
public class EntitySkeletonHorse extends EntityAnimal<io.gomint.entity.animal.EntitySkeletonHorse> implements io.gomint.entity.animal.EntitySkeletonHorse {

    /**
     * Constructs a new EntityLiving
     *
     * @param world The world in which this entity is in
     */
    public EntitySkeletonHorse(WorldAdapter world) {
        super(EntityType.SKELETON_HORSE, world);
        this.initEntity();
    }

    /**
     * Create new entity skeleton horse for API
     */
    public EntitySkeletonHorse() {
        super(EntityType.SKELETON_HORSE, null);
        this.initEntity();
    }

    private void initEntity() {
        this.size(1.3965f, 1.6f);
        this.attribute(Attribute.HEALTH);
        this.maxHealth(30);
        this.health(30);
    }

    @Override
    public void update(long currentTimeMS, float dT) {
        super.update(currentTimeMS, dT);
    }
}
