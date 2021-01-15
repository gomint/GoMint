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

@RegisterInfo(sId = "minecraft:pufferfish")
public class EntityPufferfish extends EntityAnimal<io.gomint.entity.animal.EntityPufferfish> implements io.gomint.entity.animal.EntityPufferfish {

    /**
     * Constructs a new EntityLiving
     *
     * @param world The world in which this entity is in
     */
    public EntityPufferfish(WorldAdapter world) {
        super(EntityType.PUFFERFISH, world);
        this.initEntity();
    }

    /**
     * Create new entity puffer fish for API
     */
    public EntityPufferfish() {
        super(EntityType.PUFFERFISH, null);
        this.initEntity();
    }

    private void initEntity() {
        this.size(0.35f, 0.35f);
        this.attribute(Attribute.HEALTH);
        this.maxHealth(3);
        this.health(3);
    }

    @Override
    public void update(long currentTimeMS, float dT) {
        super.update(currentTimeMS, dT);
    }

    @Override
    public Set<String> tags() {
        return EntityTags.ANIMAL;
    }

}
