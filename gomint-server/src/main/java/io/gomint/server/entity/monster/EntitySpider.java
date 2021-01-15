package io.gomint.server.entity.monster;

import io.gomint.server.entity.Attribute;
import io.gomint.server.entity.EntityLiving;
import io.gomint.server.entity.EntityTags;
import io.gomint.server.entity.EntityType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.WorldAdapter;

import java.util.Set;

@RegisterInfo( sId = "minecraft:spider" )
public class EntitySpider extends EntityLiving<io.gomint.entity.monster.EntitySpider> implements io.gomint.entity.monster.EntitySpider {

    /**
     * Constructs a new EntityLiving
     *
     * @param world The world in which this entity is in
     */
    public EntitySpider( WorldAdapter world ) {
        super( EntityType.SPIDER, world );
        this.initEntity();
    }

    /**
     * Create new entity spider for API
     */
    public EntitySpider() {
        super( EntityType.SPIDER, null );
        this.initEntity();
    }

    private void initEntity() {
        this.size( 1.4f, 0.9f );
        this.attribute( Attribute.HEALTH );
        this.maxHealth( 16 );
        this.health( 16 );
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
