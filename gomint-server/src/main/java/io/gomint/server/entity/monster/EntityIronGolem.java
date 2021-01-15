/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.entity.monster;

import io.gomint.inventory.item.ItemFlower;
import io.gomint.inventory.item.ItemIronIngot;
import io.gomint.math.Location;
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
@RegisterInfo( sId = "minecraft:iron_golem" )
public class EntityIronGolem extends EntityLiving<io.gomint.entity.monster.EntityIronGolem> implements io.gomint.entity.monster.EntityIronGolem {
    /**
     * Constructs a new EntityIronGolem
     *
     * @param world The world in which this entity is in
     */
    public EntityIronGolem( WorldAdapter world ) {
        super( EntityType.IRON_GOLEM, world );
        this.initEntity();
    }

    /**
     * Create new entity iron golem for API
     */
    public EntityIronGolem() {
        super( EntityType.IRON_GOLEM, null );
        this.initEntity();
    }

    private void initEntity() {
        this.attribute(Attribute.HEALTH);
        this.maxHealth(100);
        this.health(100);
        this.size(1.4f, 2.9f);
    }

    @Override
    protected void kill() {
        super.kill();
        
        if (dead()) {
            return;
        }
        
        // Item drops
        ThreadLocalRandom random = ThreadLocalRandom.current();
        Location location = this.location();
        this.world.dropItem(location, ItemIronIngot.create(random.nextInt(3, 6)));
        
        int amount = random.nextInt(3);
        if (amount > 0) {
            this.world.dropItem(location, ItemFlower.create(amount));
        }
    }

    @Override
    public void update( long currentTimeMS, float dT ) {
        super.update( currentTimeMS, dT );
    }

    @Override
    public Set<String> tags() {
        return EntityTags.HOSTILE_MOB;
    }
}
