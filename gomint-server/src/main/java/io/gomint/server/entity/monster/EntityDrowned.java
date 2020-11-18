package io.gomint.server.entity.monster;

import io.gomint.server.entity.Attribute;
import io.gomint.server.entity.EntityAgeable;
import io.gomint.server.entity.EntityTags;
import io.gomint.server.entity.EntityType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.WorldAdapter;

import java.util.Set;

@RegisterInfo(sId = "minecraft:drowned")
public class EntityDrowned extends EntityAgeable implements io.gomint.entity.monster.EntityDrowned {

    /**
     * Constructs a new EntityLiving
     *
     * @param world The world in which this entity is in
     */
    public EntityDrowned(WorldAdapter world) {
        super(EntityType.DROWNED, world);
        this.initEntity();
    }

    /**
     * Create new entity drowned for API
     */
    public EntityDrowned() {
        super(EntityType.DROWNED, null);
        this.initEntity();
    }

    private void initEntity() {
        this.addAttribute(Attribute.HEALTH);
        this.setMaxHealth(20);
        this.setHealth(20);
        if (this.isBaby()) {
            this.setSize(0.3f, 0.975f);
        } else {
            this.setSize(0.6f, 1.95f);
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
