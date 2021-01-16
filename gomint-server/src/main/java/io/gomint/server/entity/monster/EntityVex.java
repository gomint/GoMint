package io.gomint.server.entity.monster;

import io.gomint.server.entity.Attribute;
import io.gomint.server.entity.EntityLiving;
import io.gomint.server.entity.EntityTags;
import io.gomint.server.entity.EntityType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.WorldAdapter;

import java.util.Set;

@RegisterInfo(sId = "minecraft:vex")
public class EntityVex extends EntityLiving<io.gomint.entity.monster.EntityVex> implements io.gomint.entity.monster.EntityVex {

    /**
     * Constructs a new EntityLiving
     *
     * @param world The world in which this entity is in
     */
    public EntityVex(WorldAdapter world) {
        super(EntityType.VEX, world);
        this.initEntity();
    }

    /**
     * Create new entity stray for API
     */
    public EntityVex() {
        super(EntityType.VEX, null);
        this.initEntity();
    }

    private void initEntity() {
        this.size(0.4f, 0.8f);
        this.attribute(Attribute.HEALTH);
        this.maxHealth(14);
        this.health(14);
    }

    @Override
    public void update(long currentTimeMS, float dT) {
        super.update(currentTimeMS, dT);
    }

    @Override
    public Set<String> tags() {
        return EntityTags.HOSTILE_MOB;
    }

}
