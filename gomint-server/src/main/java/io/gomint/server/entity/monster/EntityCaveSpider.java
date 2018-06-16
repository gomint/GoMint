package io.gomint.server.entity.monster;

import io.gomint.server.entity.Attribute;
import io.gomint.server.entity.EntityLiving;
import io.gomint.server.entity.EntityType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.WorldAdapter;

@RegisterInfo( id = 40 )
public class EntityCaveSpider extends EntityLiving implements io.gomint.entity.monster.EntityCaveSpider {

    /**
     * Constructs a new EntityLiving
     *
     * @param world The world in which this entity is in
     */
    public EntityCaveSpider( WorldAdapter world ) {
        super( EntityType.CAVESPIDER, world );
        this.initEntity();
    }

    /**
     * Create new entity cave spider for API
     */
    public EntityCaveSpider() {
        super( EntityType.CAVESPIDER, null );
        this.initEntity();
    }

    private void initEntity() {
        this.setSize( 0.7f, 0.5f );
        this.addAttribute( Attribute.HEALTH );
        this.setMaxHealth( 12 );
        this.setHealth( 12 );
    }

    @Override
    public void update( long currentTimeMS, float dT ) {
        super.update( currentTimeMS, dT );
    }
}
