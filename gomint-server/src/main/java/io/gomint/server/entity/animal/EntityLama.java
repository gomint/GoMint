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

@RegisterInfo(sId = "minecraft:llama")
public class EntityLama extends EntityAgeableAnimal implements io.gomint.entity.animal.EntityLama {

    /**
     * Constructs a new EntityLiving
     *
     * @param world The world in which this entity is in
     */
    public EntityLama(WorldAdapter world) {
        super(EntityType.LLAMA, world);
        this.initEntity();
    }

    /**
     * Create new entity lama for API
     */
    public EntityLama() {
        super(EntityType.LLAMA, null);
        this.initEntity();
    }

    private void initEntity() {
        this.addAttribute(Attribute.HEALTH);
        this.setMaxHealth(30);
        this.setHealth(30);
        if (this.isBaby()) {
            this.setSize(0.9f, 1.87f);
        } else {
            this.setSize(0.45f, 0.935f);
        }
    }

    @Override
    public void update(long currentTimeMS, float dT) {
        super.update(currentTimeMS, dT);
    }
}
