package io.gomint.server.entity.monster;

import io.gomint.server.entity.Attribute;
import io.gomint.server.entity.EntityLiving;
import io.gomint.server.entity.EntityType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.WorldAdapter;

@RegisterInfo( id = 35 )
public class EntitySpider extends EntityLiving implements io.gomint.entity.monster.EntitySpider {

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
        this.setSize( 1.4f, 0.9f );
        this.addAttribute( Attribute.HEALTH );
        this.setMaxHealth( 16 );
        this.setHealth( 16 );
    }

    @Override
    public void update( long currentTimeMS, float dT ) {
        super.update( currentTimeMS, dT );
    }
}
