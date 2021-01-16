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

@RegisterInfo(sId = "minecraft:rabbit")
public class EntityRabbit extends EntityAnimal<io.gomint.entity.animal.EntityRabbit> implements io.gomint.entity.animal.EntityRabbit {

    /**
     * Constructs a new EntityLiving
     *
     * @param world The world in which this entity is in
     */
    public EntityRabbit(WorldAdapter world) {
        super(EntityType.RABBIT, world);
        this.initEntity();
    }

    /**
     * Create new entity rabbit for API
     */
    public EntityRabbit() {
        super(EntityType.RABBIT, null);
        this.initEntity();
    }

    private void initEntity() {
        this.size(0.4f, 0.5f);
        this.attribute(Attribute.HEALTH);
        this.maxHealth(20);
        this.health(20);
    }

    @Override
    public void update(long currentTimeMS, float dT) {
        super.update(currentTimeMS, dT);
    }
}
