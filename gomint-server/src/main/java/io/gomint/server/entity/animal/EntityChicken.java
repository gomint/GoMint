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
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:chicken")
public class EntityChicken extends EntityAgeableAnimal<io.gomint.entity.animal.EntityChicken> implements io.gomint.entity.animal.EntityChicken {

    /**
     * Constructs a new EntityLiving
     *
     * @param world The world in which this entity is in
     */
    public EntityChicken(WorldAdapter world) {
        super(EntityType.CHICKEN, world);
        this.initEntity();
    }

    /**
     * Create new entity chicken for API
     */
    public EntityChicken() {
        super(EntityType.CHICKEN, null);
        this.initEntity();
    }

    private void initEntity() {
        this.attribute(Attribute.HEALTH);
        this.maxHealth(8);
        this.health(8);
        if (this.baby()) {
            this.size(0.2f, 0.35f);
        } else {
            this.size(0.4f, 0.7f);
        }
    }

    @Override
    public void update(long currentTimeMS, float dT) {
        super.update(currentTimeMS, dT);
    }

}
