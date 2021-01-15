/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.entity.monster;

import io.gomint.inventory.item.ItemSnowball;
import io.gomint.server.entity.Attribute;
import io.gomint.server.entity.EntityLiving;
import io.gomint.server.entity.EntityTags;
import io.gomint.server.entity.EntityType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.WorldAdapter;

import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author joserobjr
 * @since 2021-01-12
 */
@RegisterInfo( sId = "minecraft:snow_golem" )
public class EntitySnowGolem extends EntityLiving<io.gomint.entity.monster.EntitySnowGolem> implements io.gomint.entity.monster.EntitySnowGolem {
    /**
     * Constructs a new EntitySnowGolem
     *
     * @param world The world in which this entity is in
     */
    public EntitySnowGolem(WorldAdapter world ) {
        super( EntityType.SNOW_GOLEM, world );
        this.initEntity();
    }

    /**
     * Create new entity snow golem for API
     */
    public EntitySnowGolem() {
        super( EntityType.SNOW_GOLEM, null );
        this.initEntity();
    }

    private void initEntity() {
        this.attribute(Attribute.HEALTH);
        this.maxHealth(4);
        this.health(4);
        this.size(0.4f, 1.8f);
    }

    @Override
    protected void kill() {
        super.kill();

        if (dead()) {
            return;
        }
        
        // Item drops
        int amount = ThreadLocalRandom.current().nextInt(16);
        if (amount > 0) {
            this.world.dropItem(this.location(), ItemSnowball.create(amount));
        }
    }

    @Override
    public void update( long currentTimeMS, float dT ) {
        super.update( currentTimeMS, dT );
    }

    @Override
    public Set<String> tags() {
        return EntityTags.RANGED_HOSTILE_MOB;
    }
}
