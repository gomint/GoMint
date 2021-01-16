package io.gomint.server.entity.monster;

import io.gomint.server.entity.Attribute;
import io.gomint.server.entity.EntityLiving;
import io.gomint.server.entity.EntityTags;
import io.gomint.server.entity.EntityType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.WorldAdapter;

import java.util.Set;

@RegisterInfo( sId = "minecraft:ghast" )
public class EntityGhast extends EntityLiving<io.gomint.entity.monster.EntityGhast> implements io.gomint.entity.monster.EntityGhast {

    /**
     * Constructs a new EntityLiving
     *
     * @param world The world in which this entity is in
     */
    public EntityGhast( WorldAdapter world ) {
        super( EntityType.GHAST, world );
        this.initEntity();
    }

    /**
     * Create new entity ghast for API
     */
    public EntityGhast() {
        super( EntityType.GHAST, null );
        this.initEntity();
    }

    private void initEntity() {
        this.size( 4.0f, 4.0f );
        this.attribute( Attribute.HEALTH );
        this.maxHealth( 10);
        this.health( 10 );
    }

    @Override
    public void update( long currentTimeMS, float dT ) {
        super.update( currentTimeMS, dT );
    }

    @Override
    public Set<String> tags() {
        return EntityTags.RANGED_HOSTILE_MOB;
    }

}
