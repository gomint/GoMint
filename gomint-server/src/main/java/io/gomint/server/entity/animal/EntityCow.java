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
 * @author BlackyPaw
 * @author geNAZt
 * @version 1.2
 */
@RegisterInfo(sId = "minecraft:cow")
public class EntityCow extends EntityAgeableAnimal implements io.gomint.entity.animal.EntityCow {

    /**
     * Constructs a new EntityLiving
     *
     * @param world The world in which this entity is in
     */
    public EntityCow(WorldAdapter world) {
        super(EntityType.COW, world);
        this.initEntity();
    }

    /**
     * Create new entity cow for API
     */
    public EntityCow() {
        super(EntityType.COW, null);
        this.initEntity();
    }

    private void initEntity() {
        this.addAttribute(Attribute.HEALTH);
        this.setMaxHealth(20);
        this.setHealth(20);
        if (this.isBaby()) {
            this.setSize(0.45f, 0.7f);
        } else {
            this.setSize(0.9f, 1.4f);
        }
    }

    @Override
    public void update(long currentTimeMS, float dT) {
        super.update(currentTimeMS, dT);
    }

    @Override
    protected void setupAI() {
        // Kick of AI
        // this.behaviour.getMachine().switchState( new AIPassiveIdleMovement( this.behaviour.getMachine(), this.world, new PathfindingEngine( this.getTransform() ) ) );
    }

}
