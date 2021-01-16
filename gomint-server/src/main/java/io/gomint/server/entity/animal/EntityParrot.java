/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.entity.animal;

import io.gomint.server.entity.Attribute;
import io.gomint.server.entity.EntityFlag;
import io.gomint.server.entity.EntityType;
import io.gomint.server.entity.metadata.MetadataContainer;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.WorldAdapter;

@RegisterInfo(sId = "minecraft:parrot")
public class EntityParrot extends EntityAnimal<io.gomint.entity.animal.EntityParrot> implements io.gomint.entity.animal.EntityParrot {

    /**
     * Constructs a new EntityLiving
     *
     * @param world The world in which this entity is in
     */
    public EntityParrot(WorldAdapter world) {
        super(EntityType.PARROT, world);
        this.initEntity();
    }

    /**
     * Create new entity parrot for API
     */
    public EntityParrot() {
        super(EntityType.PARROT, null);
        this.initEntity();
    }

    private void initEntity() {
        this.size(0.5f, 0.9f);
        this.attribute(Attribute.HEALTH);
        this.maxHealth(6);
        this.health(6);
    }

    @Override
    public boolean dancing() {
        return this.metadataContainer.getDataFlag(MetadataContainer.DATA_INDEX, EntityFlag.DANCING);
    }

    @Override
    public EntityParrot dancing(boolean value) {
        this.metadataContainer.setDataFlag(MetadataContainer.DATA_INDEX, EntityFlag.DANCING, value);
        return this;
    }

    @Override
    public void update(long currentTimeMS, float dT) {
        super.update(currentTimeMS, dT);
    }

}
