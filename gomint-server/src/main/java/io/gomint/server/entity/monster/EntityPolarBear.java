package io.gomint.server.entity.monster;

import io.gomint.server.entity.Attribute;
import io.gomint.server.entity.EntityAgeable;
import io.gomint.server.entity.EntityTags;
import io.gomint.server.entity.EntityType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.WorldAdapter;

import java.util.Set;

@RegisterInfo( sId = "minecraft:polar_bear" )
public class EntityPolarBear extends EntityAgeable<io.gomint.entity.monster.EntityPolarBear> implements io.gomint.entity.monster.EntityPolarBear {

    /**
     * Constructs a new EntityLiving
     *
     * @param world The world in which this entity is in
     */
    public EntityPolarBear( WorldAdapter world ) {
        super( EntityType.POLAR_BEAR, world );
        this.initEntity();
    }

    /**
     * Create new entity polar bear for API
     */
    public EntityPolarBear() {
        super( EntityType.POLAR_BEAR, null );
        this.initEntity();
    }

    private void initEntity() {
        this.attribute(Attribute.HEALTH);
        this.maxHealth(30);
        this.health(30);
        if(this.baby()) {
            this.size(0.7f, 0.7f);
        }else{
            this.size(1.3f, 1.4f);
        }
    }

    @Override
    public void update( long currentTimeMS, float dT ) {
        super.update( currentTimeMS, dT );
    }

    @Override
    public Set<String> tags() {
        return EntityTags.HOSTILE_MOB;
    }

}
