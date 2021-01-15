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
@RegisterInfo(sId = "minecraft:fox")
public class EntityFox extends EntityAgeableAnimal<io.gomint.entity.animal.EntityFox> implements io.gomint.entity.animal.EntityFox {

    /**
     * Constructs a new EntityLiving
     *
     * @param world The world in which this entity is in
     */
    public EntityFox(WorldAdapter world) {
        super(EntityType.FOX, world);
        this.initEntity();
    }

    public EntityFox() {
        super(EntityType.FOX, null);
        this.initEntity();
    }

    private void initEntity() {
        this.attribute(Attribute.HEALTH);
        this.maxHealth(20);
        this.health(20);
        this.size(0.7f, 0.6f);
        //No Information for baby size ??
    }

    @Override
    public void update(long currentTimeMS, float dT) {
        super.update(currentTimeMS, dT);
    }

}
