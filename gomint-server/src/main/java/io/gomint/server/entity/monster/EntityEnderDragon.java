package io.gomint.server.entity.monster;

import io.gomint.server.entity.Attribute;
import io.gomint.server.entity.EntityLiving;
import io.gomint.server.entity.EntityTags;
import io.gomint.server.entity.EntityType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.WorldAdapter;

import java.util.Set;

@RegisterInfo(sId = "minecraft:ender_dragon")
public class EntityEnderDragon extends EntityLiving<io.gomint.entity.monster.EntityEnderDragon> implements io.gomint.entity.monster.EntityEnderDragon {

    /**
     * Constructs a new EntityLiving
     *
     * @param world The world in which this entity is in
     */
    public EntityEnderDragon(WorldAdapter world) {
        super(EntityType.ENDER_DRAGON, world);
        this.initEntity();
    }

    /**
     * Create new entity ender dragon for API
     */
    public EntityEnderDragon() {
        super(EntityType.ENDER_DRAGON, null);
        this.initEntity();
    }

    private void initEntity() {
        this.size(13.0f, 4.0f);
        this.attribute(Attribute.HEALTH);
        this.maxHealth(200);
        this.health(200);
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
