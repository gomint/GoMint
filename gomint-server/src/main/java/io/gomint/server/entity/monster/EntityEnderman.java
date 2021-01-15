package io.gomint.server.entity.monster;

import io.gomint.server.entity.Attribute;
import io.gomint.server.entity.EntityLiving;
import io.gomint.server.entity.EntityTags;
import io.gomint.server.entity.EntityType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.WorldAdapter;

import java.util.Set;

@RegisterInfo( sId = "minecraft:enderman" )
public class EntityEnderman extends EntityLiving<io.gomint.entity.monster.EntityEnderman> implements io.gomint.entity.monster.EntityEnderman {

    /**
     * Constructs a new EntityLiving

     * @param world The world in which this entity is in
     */
    public EntityEnderman( WorldAdapter world ) {
        super( EntityType.ENDERMAN, world );
        this.initEntity();
    }

    /**
     * Create new entity enderman for API
     */
    public EntityEnderman() {
        super( EntityType.ENDERMAN, null );
        this.initEntity();
    }

    private void initEntity() {
        this.size( 0.6f, 2.9f );
        this.attribute( Attribute.HEALTH );
        this.maxHealth( 40 );
        this.health( 40 );
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
