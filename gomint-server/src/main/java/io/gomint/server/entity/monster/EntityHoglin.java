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
public class EntityHoglin extends EntityAgeable implements io.gomint.entity.monster.EntityHoglin {

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
        this.addAttribute(Attribute.HEALTH);
        this.setHealth(40);
        this.setMaxHealth(40);
        if (this.isBaby()) {
            this.setSize(0.45f, 0.45f);
        } else {
            this.setSize(0.9f, 0.9f);
        }
    }

    @Override
    public void update(long currentTimeMS, float dT) {
        super.update(currentTimeMS, dT);
    }

    @Override
    public Set<String> getTags() {
        return EntityTags.HOSTILE_MOB;
    }

}
