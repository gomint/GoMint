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
@RegisterInfo(sId = "minecraft:donkey")
public class EntityDonkey extends EntityAgeableAnimal implements io.gomint.entity.animal.EntityDonkey {

    /**
     * Constructs a new EntityLiving
     *
     * @param world The world in which this entity is in
     */
    public EntityDonkey(WorldAdapter world) {
        super(EntityType.DONKEY, world);
        this.initEntity();
    }

    /**
     * Create new entity donkey for API
     */
    public EntityDonkey() {
        super(EntityType.DONKEY, null);
        this.initEntity();
    }

    private void initEntity() {
        this.addAttribute(Attribute.HEALTH);
        this.setMaxHealth(30);
        this.setHealth(30);
        if (this.isBaby()) {
            this.setSize(0.6982f, 0.75f);
        } else {
            this.setSize(1.3965f, 1.5f);
        }
    }

    @Override
    public void update(long currentTimeMS, float dT) {
        super.update(currentTimeMS, dT);
    }

}
