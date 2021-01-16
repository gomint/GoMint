package io.gomint.server.entity.monster;

import io.gomint.server.entity.Attribute;
import io.gomint.server.entity.EntityLiving;
import io.gomint.server.entity.EntityTags;
import io.gomint.server.entity.EntityType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.WorldAdapter;

import java.util.Set;

@RegisterInfo(sId = "minecraft:shulker")
public class EntityShulker extends EntityLiving<io.gomint.entity.monster.EntityShulker> implements io.gomint.entity.monster.EntityShulker {

    /**
     * Constructs a new EntityLiving
     *
     * @param world The world in which this entity is in
     */
    public EntityShulker(WorldAdapter world) {
        super(EntityType.SHULKER, world);
        this.initEntity();
    }

    /**
     * Create new entity shulker for API
     */
    public EntityShulker() {
        super(EntityType.SHULKER, null);
        this.initEntity();
    }

    private void initEntity() {
        this.size(1.0f, 1.0f);
        this.attribute(Attribute.HEALTH);
        this.maxHealth(30);
        this.health(30);
    }

    @Override
    public void update(long currentTimeMS, float dT) {
        super.update(currentTimeMS, dT);
    }

    @Override
    public Set<String> tags() {
        return EntityTags.RANGED_HOSTILE_MOB;
    }

}
