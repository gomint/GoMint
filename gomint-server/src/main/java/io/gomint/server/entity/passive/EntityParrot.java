package io.gomint.server.entity.passive;

import io.gomint.server.entity.Attribute;
import io.gomint.server.entity.EntityLiving;
import io.gomint.server.entity.EntityType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.WorldAdapter;

@RegisterInfo( id = 30 )
public class EntityParrot extends EntityLiving implements io.gomint.entity.passive.EntityParrot {

    /**
     * Constructs a new EntityLiving
     *
     * @param world The world in which this entity is in
     */
    public EntityParrot( WorldAdapter world ) {
        super( EntityType.PARROT, world );
        this.initEntity();
    }

    /**
     * Create new entity parrot for API
     */
    public EntityParrot() {
        super( EntityType.PARROT, null );
        this.initEntity();
    }

    private void initEntity() {
        this.setSize( 0.5f, 0.9f );
        this.addAttribute( Attribute.HEALTH );
        this.setMaxHealth( 6 );
        this.setHealth( 6 );
    }

    @Override
    public void update( long currentTimeMS, float dT ) {
        super.update( currentTimeMS, dT );
    }
}
