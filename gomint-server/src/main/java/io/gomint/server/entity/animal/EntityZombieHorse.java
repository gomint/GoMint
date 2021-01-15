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

@RegisterInfo(sId = "minecraft:zombie_horse")
public class EntityZombieHorse extends EntityAnimal<io.gomint.entity.animal.EntityZombieHorse> implements io.gomint.entity.animal.EntityZombieHorse {

    /**
     * Constructs a new EntityLiving
     *
     * @param world The world in which this entity is in
     */
    public EntityZombieHorse(WorldAdapter world) {
        super(EntityType.ZOMBIE_HORSE, world);
        this.initEntity();
    }

    /**
     * Create new entity zombie horse for API
     */
    public EntityZombieHorse() {
        super(EntityType.ZOMBIE_HORSE, null);
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
