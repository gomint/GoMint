package io.gomint.server.entity.monster;

import io.gomint.server.entity.Attribute;
import io.gomint.server.entity.EntityAgeable;
import io.gomint.server.entity.EntityTags;
import io.gomint.server.entity.EntityType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.WorldAdapter;

import java.util.Set;

/**
 * @author KingAli
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:hoglin")
public class EntityHoglin extends EntityAgeable<io.gomint.entity.monster.EntityHoglin> implements io.gomint.entity.monster.EntityHoglin {

    /**
     * Constructs a new EntityLiving
     *
     * @param world The world in which this entity is in
     */
    public EntityHoglin(WorldAdapter world) {
        super(EntityType.HOGLIN, world);
        this.initEntity();
    }

    public EntityHoglin() {
        super(EntityType.HOGLIN, null);
        this.initEntity();
    }

    private void initEntity() {
        this.attribute(Attribute.HEALTH);
        this.health(40);
        this.maxHealth(40);
        if (this.baby()) {
            this.size(0.45f, 0.45f);
        } else {
            this.size(0.9f, 0.9f);
        }
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
