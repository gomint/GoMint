package io.gomint.server.entity.monster;

import io.gomint.server.entity.Attribute;
import io.gomint.server.entity.EntityLiving;
import io.gomint.server.entity.EntityTags;
import io.gomint.server.entity.EntityType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.WorldAdapter;

import java.util.Set;

@RegisterInfo(sId = "minecraft:evocation_illager")
public class EntityEvoker extends EntityLiving<io.gomint.entity.monster.EntityEvoker> implements io.gomint.entity.monster.EntityEvoker {

    /**
     * Constructs a new EntityLiving
     *
     * @param world The world in which this entity is in
     */
    public EntityEvoker(WorldAdapter world) {
        super(EntityType.EVOKER, world);
        this.initEntity();
    }

    /**
     * create a new entity evoker for API
     */
    public EntityEvoker() {
        super(EntityType.EVOKER, null);
        this.initEntity();
    }

    private void initEntity() {
        this.size(0.6f, 1.96f);
        this.attribute(Attribute.HEALTH);
        this.maxHealth(24);
        this.health(24);
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
