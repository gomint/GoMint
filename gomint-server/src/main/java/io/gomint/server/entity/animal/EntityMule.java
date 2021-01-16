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

@RegisterInfo(sId = "minecraft:mule")
public class EntityMule extends EntityAgeableAnimal<io.gomint.entity.animal.EntityMule> implements io.gomint.entity.animal.EntityMule {

    /**
     * Constructs a new EntityLiving
     *
     * @param world The world in which this entity is in
     */
    public EntityMule(WorldAdapter world) {
        super(EntityType.MULE, world);
        this.initEntity();
    }

    /**
     * Create new entity mule for API
     */
    public EntityMule() {
        super(EntityType.MULE, null);
        this.initEntity();
    }

    private void initEntity() {
        this.attribute(Attribute.HEALTH);
        this.maxHealth(30);
        this.health(30);
        if (this.baby()) {
            this.size(0.6982f, 0.8f);
        } else {
            this.size(1.3965f, 1.6f);
        }
    }

    @Override
    public void update(long currentTimeMS, float dT) {
        super.update(currentTimeMS, dT);
    }

}
