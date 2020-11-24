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
public class EntityStrider extends EntityAgeableAnimal implements io.gomint.entity.animal.EntityStrider {

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
        this.addAttribute(Attribute.HEALTH);
        this.setHealth(15);
        this.setMaxHealth(15);
        if (this.isBaby()) {
            this.setSize(0.45f, 0.85f);
        } else {
            this.setSize(0.9f, 1.7f);
        }
    }

    @Override
    public void update(long currentTimeMS, float dT) {
        super.update(currentTimeMS, dT);
    }

}
