/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.entity.animal;

import io.gomint.server.entity.Attribute;
import io.gomint.server.entity.EntityTags;
import io.gomint.server.entity.EntityType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.WorldAdapter;

import java.util.Set;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:bat")
public class EntityBat extends EntityAnimal<io.gomint.entity.animal.EntityBat> implements io.gomint.entity.animal.EntityBat {

    /**
     * Constructs a new EntityLiving
     *
     * @param world The world in which this entity is in
     */
    public EntityBat(WorldAdapter world) {
        super(EntityType.BAT, world);
        this.initEntity();
    }

    /**
     * Create new entity bat for API
     */
    public EntityBat() {
        super(EntityType.BAT, null);
        this.initEntity();
    }

    private void initEntity() {
        this.size(0.5f, 0.9f);
        this.attribute(Attribute.HEALTH);
        this.maxHealth(12);
        this.health(12);
    }

    @Override
    public void update(long currentTimeMS, float dT) {
        super.update(currentTimeMS, dT);
    }

    @Override
    public Set<String> tags() {
        return EntityTags.CREATURE;
    }

}
