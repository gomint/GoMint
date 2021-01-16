/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.entity.animal;

import io.gomint.inventory.item.ItemString;
import io.gomint.math.Location;
import io.gomint.server.entity.Attribute;
import io.gomint.server.entity.EntityType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.WorldAdapter;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author joserobjr
 * @since 2021-01-12
 */
@RegisterInfo(sId = "minecraft:cat")
public class EntityCat extends EntityAgeableAnimal<io.gomint.entity.animal.EntityCat> implements io.gomint.entity.animal.EntityCat {
    
    /**
     * Constructs a new EntityCat
     *
     * @param world The world in which this entity is in
     */
    public EntityCat(WorldAdapter world) {
        super(EntityType.CAT, world);
        this.initEntity();
    }

    /**
     * Create new entity wolf for API
     */
    public EntityCat() {
        super(EntityType.CAT, null);
        this.initEntity();
    }

    private void initEntity() {
        this.attribute(Attribute.HEALTH);
        this.maxHealth(10);
        this.health(10);
        if (this.baby()) {
            this.size(0.24f, 0.28f);
        } else {
            this.size(0.48f, 0.56f);
        }
    }

    @Override
    protected void kill() {
        super.kill();

        if (dead() || baby()) {
            return;
        }
        
        // Entity drops
        Location location = this.location();
        
        ThreadLocalRandom random = ThreadLocalRandom.current();
        int amount = random.nextInt(3);
        if (amount > 0) {
            this.world.dropItem(location, ItemString.create(amount));
        }
        
        if (isLastDamageCausedByPlayer()) {
            this.world.createExpOrb(location, random.nextInt(1, 4));
        }
    }

    @Override
    public void update(long currentTimeMS, float dT) {
        super.update(currentTimeMS, dT);
    }
}
