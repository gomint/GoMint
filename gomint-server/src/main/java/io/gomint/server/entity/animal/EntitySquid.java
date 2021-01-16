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

@RegisterInfo(sId = "minecraft:squid")
public class EntitySquid extends EntityAnimal<io.gomint.entity.animal.EntitySquid> implements io.gomint.entity.animal.EntitySquid {

    /**
     * Constructs a new EntityLiving
     *
     * @param world The world in which this entity is in
     */
    public EntitySquid(WorldAdapter world) {
        super(EntityType.SQUID, world);
        this.initEntity();
    }

    /**
     * Create new entity squid for API
     */
    public EntitySquid() {
        super(EntityType.SQUID, null);
        this.initEntity();
    }

    private void initEntity() {
        this.size(0.8f, 0.8f);
        this.attribute(Attribute.HEALTH);
        this.maxHealth(20);
        this.health(20);
    }

    @Override
    public void update(long currentTimeMS, float dT) {
        super.update(currentTimeMS, dT);
    }

    @Override
    public Set<String> tags() {
        return EntityTags.WATER_CREATURE;
    }

}
