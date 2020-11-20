package io.gomint.server.entity.monster;

import io.gomint.server.entity.Attribute;
import io.gomint.server.entity.EntityLiving;
import io.gomint.server.entity.EntityTags;
import io.gomint.server.entity.EntityType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.WorldAdapter;

import java.util.Set;

@RegisterInfo(sId = "minecraft:evocation_illager")
public class EntityEvoker extends EntityLiving implements io.gomint.entity.monster.EntityEvoker {

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
        this.setSize(0.6f, 1.96f);
        this.addAttribute(Attribute.HEALTH);
        this.setMaxHealth(24);
        this.setHealth(24);
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
