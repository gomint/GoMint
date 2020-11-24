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

@RegisterInfo(sId = "minecraft:tropicalfish")
public class EntityTropicalFish extends EntityAnimal implements io.gomint.entity.animal.EntityTropicalFish {

    /**
     * Constructs a new EntityLiving
     *
     * @param world The world in which this entity is in
     */
    public EntityTropicalFish(WorldAdapter world) {
        super(EntityType.TROPICALFISH, world);
        this.initEntity();
    }

    /**
     * Create new entity salmon for API
     */
    public EntityTropicalFish() {
        super(EntityType.TROPICALFISH, null);
        this.initEntity();
    }

    private void initEntity() {
        this.setSize(0.5f, 0.4f);
        this.addAttribute(Attribute.HEALTH);
        this.setMaxHealth(3);
        this.setHealth(3);
    }

    @Override
    public void update(long currentTimeMS, float dT) {
        super.update(currentTimeMS, dT);
    }

}
