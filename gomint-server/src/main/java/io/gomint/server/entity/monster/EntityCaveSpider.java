package io.gomint.server.entity.monster;

import io.gomint.server.entity.Attribute;
import io.gomint.server.entity.EntityLiving;
import io.gomint.server.entity.EntityTags;
import io.gomint.server.entity.EntityType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.WorldAdapter;

import java.util.Set;

@RegisterInfo( sId = "minecraft:cave_spider" )
public class EntityCaveSpider extends EntityLiving<io.gomint.entity.monster.EntityCaveSpider> implements io.gomint.entity.monster.EntityCaveSpider {

    /**
     * Constructs a new EntityLiving
     *
     * @param world The world in which this entity is in
     */
    public EntityCaveSpider( WorldAdapter world ) {
        super( EntityType.CAVE_SPIDER, world );
        this.initEntity();
    }

    /**
     * Create new entity cave spider for API
     */
    public EntityCaveSpider() {
        super( EntityType.CAVE_SPIDER, null );
        this.initEntity();
    }

    private void initEntity() {
        this.size( 0.7f, 0.5f );
        this.attribute( Attribute.HEALTH );
        this.maxHealth( 12 );
        this.health( 12 );
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
