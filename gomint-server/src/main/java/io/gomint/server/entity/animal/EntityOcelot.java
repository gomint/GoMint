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

@RegisterInfo(sId = "minecraft:ocelot")
public class EntityOcelot extends EntityAnimal implements io.gomint.entity.animal.EntityOcelot {

    /**
     * Constructs a new EntityLiving
     *
     * @param world The world in which this entity is in
     */
    public EntityOcelot(WorldAdapter world) {
        super(EntityType.OCELOT, world);
        this.initEntity();
    }

    /**
     * Create new entity ocelot for API
     */
    public EntityOcelot() {
        super(EntityType.OCELOT, null);
        this.initEntity();
    }

    private void initEntity() {
        this.setSize(0.6f, 0.7f);
        this.addAttribute(Attribute.HEALTH);
        this.setMaxHealth(16);
        this.setHealth(16);
    }

    @Override
    public void update(long currentTimeMS, float dT) {
        super.update(currentTimeMS, dT);
    }
}
