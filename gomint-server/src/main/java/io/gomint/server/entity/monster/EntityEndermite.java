package io.gomint.server.entity.monster;

import io.gomint.server.entity.Attribute;
import io.gomint.server.entity.EntityLiving;
import io.gomint.server.entity.EntityTags;
import io.gomint.server.entity.EntityType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.WorldAdapter;

import java.util.Set;

@RegisterInfo(sId = "minecraft:endermite")
public class EntityEndermite extends EntityLiving<io.gomint.entity.monster.EntityEndermite> implements io.gomint.entity.monster.EntityEndermite {

    /**
     * Constructs a new EntityLiving
     *
     * @param world The world in which this entity is in
     */
    public EntityEndermite(WorldAdapter world) {
        super(EntityType.ENDERMITE, world);
        this.initEntity();
    }

    /**
     * Create new entity blaze for API
     */
    public EntityEndermite() {
        super(EntityType.ENDERMITE, null);
        this.initEntity();
    }

    private void initEntity() {
        this.size(0.4f, 0.3f);
        this.attribute(Attribute.HEALTH);
        this.maxHealth(8);
        this.health(8);
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
