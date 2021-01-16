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

/**
 * @author KingAli
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:strider")
public class EntityStrider extends EntityAgeableAnimal<io.gomint.entity.animal.EntityStrider> implements io.gomint.entity.animal.EntityStrider {

    /**
     * Constructs a new EntityLiving
     *
     * @param world The world in which this entity is in
     */
    public EntityStrider(WorldAdapter world) {
        super(EntityType.STRIDER, world);
        this.initEntity();
    }


    public EntityStrider() {
        super(EntityType.STRIDER, null);
        this.initEntity();
    }

    private void initEntity() {
        this.attribute(Attribute.HEALTH);
        this.health(15);
        this.maxHealth(15);
        if (this.baby()) {
            this.size(0.45f, 0.85f);
        } else {
            this.size(0.9f, 1.7f);
        }
    }

    @Override
    public void update(long currentTimeMS, float dT) {
        super.update(currentTimeMS, dT);
    }

}
