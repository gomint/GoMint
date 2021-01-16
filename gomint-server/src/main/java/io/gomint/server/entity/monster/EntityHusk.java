package io.gomint.server.entity.monster;

import io.gomint.server.entity.Attribute;
import io.gomint.server.entity.EntityAgeable;
import io.gomint.server.entity.EntityTags;
import io.gomint.server.entity.EntityType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.WorldAdapter;

import java.util.Set;

@RegisterInfo( sId = "minecraft:husk" )
public class EntityHusk extends EntityAgeable<io.gomint.entity.monster.EntityHusk> implements io.gomint.entity.monster.EntityHusk {

    /**
     * Constructs a new EntityLiving
     *
     * @param world The world in which this entity is in
     */
    public EntityHusk( WorldAdapter world ) {
        super( EntityType.HUSK, world );
        this.initEntity();
    }

    /**
     * Create new entity husk for API
     */
    public EntityHusk() {
        super( EntityType.HUSK, null );
        this.initEntity();
    }

    private void initEntity() {
        this.attribute(Attribute.HEALTH);
        this.maxHealth(20);
        this.health(20);
        if(this.baby()) {
            this.size(0.3f, 0.975f);
        }else{
            this.size(0.6f, 1.95f);
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
