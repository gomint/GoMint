package io.gomint.server.entity.monster;

import io.gomint.server.entity.Attribute;
import io.gomint.server.entity.EntityLiving;
import io.gomint.server.entity.EntityTags;
import io.gomint.server.entity.EntityType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.WorldAdapter;

import java.util.Set;

@RegisterInfo(sId = "minecraft:skeleton")
public class EntitySkeleton extends EntityLiving implements io.gomint.entity.monster.EntitySkeleton {

    /**
     * Constructs a new EntityLiving
     *
     * @param world The world in which this entity is in
     */
    public EntitySkeleton(WorldAdapter world) {
        super(EntityType.SKELETON, world);
        this.initEntity();
    }

    /**
     * Create new entity skeleton for API
     */
    public EntitySkeleton() {
        super(EntityType.SKELETON, null);
        this.initEntity();
    }

    private void initEntity() {
        this.setSize(0.6f, 1.99f);
        this.addAttribute(Attribute.HEALTH);
        this.setMaxHealth(20);
        this.setHealth(20);
    }

    @Override
    public void update(long currentTimeMS, float dT) {
        super.update(currentTimeMS, dT);
    }

    @Override
    public Set<String> getTags() {
        return EntityTags.RANGED_HOSTILE_MOB;
    }

}
