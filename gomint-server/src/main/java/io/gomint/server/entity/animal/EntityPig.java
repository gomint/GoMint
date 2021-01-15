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

@RegisterInfo(sId = "minecraft:pig")
public class EntityPig extends EntityAgeableAnimal<io.gomint.entity.animal.EntityPig> implements io.gomint.entity.animal.EntityPig {

    /**
     * Constructs a new EntityLiving
     *
     * @param world The world in which this entity is in
     */
    public EntityPig(WorldAdapter world) {
        super(EntityType.PIG, world);
        this.initEntity();
    }

    /**
     * Create new entity pig for API
     */
    public EntityPig() {
        super(EntityType.PIG, null);
        this.initEntity();
    }

    private void initEntity() {
        this.attribute(Attribute.HEALTH);
        this.maxHealth(20);
        this.health(20);
        if (this.baby()) {
            this.size(0.45f, 0.45f);
        } else {
            this.size(0.9f, 0.9f);
        }
    }

    @Override
    public void update(long currentTimeMS, float dT) {
        super.update(currentTimeMS, dT);
    }
}
