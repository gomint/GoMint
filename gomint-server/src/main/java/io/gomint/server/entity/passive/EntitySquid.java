package io.gomint.server.entity.passive;

import io.gomint.server.entity.Attribute;
import io.gomint.server.entity.EntityLiving;
import io.gomint.server.entity.EntityType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.WorldAdapter;

@RegisterInfo( id = 17 )
public class EntitySquid extends EntityLiving implements io.gomint.entity.passive.EntitySquid{

    /**
     * Constructs a new EntityLiving
     *
     * @param world The world in which this entity is in
     */
    public EntitySquid( WorldAdapter world ) {
        super( EntityType.SQUID, world );
        this.initEntity();
    }

    /**
     * Create new entity squid for API
     */
    public EntitySquid() {
        super( EntityType.SQUID, null );
        this.initEntity();
    }

    private void initEntity() {
        this.setSize( 0.8f, 0.8f );
        this.addAttribute( Attribute.HEALTH );
        this.setMaxHealth( 20 );
        this.setHealth( 20 );
    }

    @Override
    public void update( long currentTimeMS, float dT ) {
        super.update( currentTimeMS, dT );
    }
}
