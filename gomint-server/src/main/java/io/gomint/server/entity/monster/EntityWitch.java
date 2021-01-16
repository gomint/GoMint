package io.gomint.server.entity.monster;

import io.gomint.server.entity.Attribute;
import io.gomint.server.entity.EntityLiving;
import io.gomint.server.entity.EntityTags;
import io.gomint.server.entity.EntityType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.WorldAdapter;

import java.util.Set;

@RegisterInfo( sId = "minecraft:witch" )
public class EntityWitch extends EntityLiving<io.gomint.entity.monster.EntityWitch> implements io.gomint.entity.monster.EntityWitch {

    /**
     * Constructs a new EntityLiving
     *
     * @param world The world in which this entity is in
     */
    public EntityWitch( WorldAdapter world ) {
        super( EntityType.WITCH, world );
        this.initEntity();
    }

    /**
     * Create new entity witch for API
     */
    public EntityWitch() {
        super( EntityType.WITCH, null );
        this.initEntity();
    }

    private void initEntity() {
        this.size( 0.6f, 1.95f );
        this.attribute( Attribute.HEALTH );
        this.maxHealth( 26 );
        this.health( 26 );
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
