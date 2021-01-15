package io.gomint.server.entity.monster;

import io.gomint.server.entity.Attribute;
import io.gomint.server.entity.EntityLiving;
import io.gomint.server.entity.EntityTags;
import io.gomint.server.entity.EntityType;
import io.gomint.server.entity.metadata.MetadataContainer;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.WorldAdapter;

import java.util.Set;

/**
 * @author LucGames
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:magma_cube")
public class EntityMagmaCube extends EntityLiving<io.gomint.entity.monster.EntityMagmaCube> implements io.gomint.entity.monster.EntityMagmaCube {

    /**
     * Constructs a new EntityLiving
     *
     * @param world The world in which this entity is in
     */
    public EntityMagmaCube(WorldAdapter world) {
        super(EntityType.MAGMA_CUBE, world);
        this.initEntity();
    }

    /**
     * Create new entity magma cube for API
     */
    public EntityMagmaCube() {
        super(EntityType.MAGMA_CUBE, null);
        this.initEntity();
    }

    private void initEntity() {
        this.attribute(Attribute.HEALTH);

        this.sizeFactor(4);
    }

    @Override
    public EntityMagmaCube sizeFactor(int factor) {
        float newHealth = (float) Math.pow(2, factor);
        this.maxHealth(newHealth);
        this.health(newHealth);
        this.size(factor * 0.51f, factor * 0.51f);

        this.metadataContainer.putInt(MetadataContainer.DATA_VARIANT, factor);
        return this;
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
