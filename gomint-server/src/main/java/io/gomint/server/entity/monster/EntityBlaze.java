package io.gomint.server.entity.monster;

import io.gomint.server.entity.Attribute;
import io.gomint.server.entity.EntityLiving;
import io.gomint.server.entity.EntityTags;
import io.gomint.server.entity.EntityType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.WorldAdapter;

import java.util.Set;

@RegisterInfo( sId = "minecraft:blaze" )
public class EntityBlaze extends EntityLiving<io.gomint.entity.monster.EntityBlaze> implements io.gomint.entity.monster.EntityBlaze {

    /**
     * Constructs a new EntityLiving
     *
     * @param world The world in which this entity is in
     */
    public EntityBlaze( WorldAdapter world ) {
        super( EntityType.BLAZE, world );
        this.initEntity();
    }

    /**
     * Create new entity blaze for API
     */
    public EntityBlaze() {
        super( EntityType.BLAZE, null );
        this.initEntity();
    }

    private void initEntity() {
        this.size( 0.6f, 1.8f );
        this.attribute( Attribute.HEALTH );
        this.maxHealth( 20 );
        this.health( 20 );
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
