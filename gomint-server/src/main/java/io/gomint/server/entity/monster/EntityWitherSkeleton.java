package io.gomint.server.entity.monster;

import io.gomint.server.entity.Attribute;
import io.gomint.server.entity.EntityLiving;
import io.gomint.server.entity.EntityType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.WorldAdapter;

@RegisterInfo( id = 48 )
public class EntityWitherSkeleton extends EntityLiving implements io.gomint.entity.monster.EntityWitherSkeleton {

    /**
     * Constructs a new EntityLiving
     *
     * @param world The world in which this entity is in
     */
    public EntityWitherSkeleton( WorldAdapter world ) {
        super( EntityType.WITHER_SKELETON, world );
        this.initEntity();
    }

    /**
     * Create new entity wither skeleton for API
     */
    public EntityWitherSkeleton() {
        super( EntityType.WITHER_SKELETON, null );
        this.initEntity();
    }

    private void initEntity() {
        this.setSize( 0.7f, 2.4f );
        this.addAttribute( Attribute.HEALTH );
        this.setMaxHealth( 20 );
        this.setHealth( 20 );
    }

    @Override
    public void update( long currentTimeMS, float dT ) {
        super.update( currentTimeMS, dT );
    }
}
