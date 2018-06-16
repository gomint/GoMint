package io.gomint.server.entity.passive;

import io.gomint.server.entity.Attribute;
import io.gomint.server.entity.EntityLiving;
import io.gomint.server.entity.EntityType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.WorldAdapter;

@RegisterInfo( id = 25 )
public class EntityMule extends EntityLiving implements io.gomint.entity.passive.EntityMule {
    /**
     * Constructs a new EntityLiving
     *
     * @param world The world in which this entity is in
     */
    public EntityMule( WorldAdapter world ) {
        super( EntityType.MULE, world );
        this.initEntity();
    }

    /**
     * Create new entity mule for API
     */
    public EntityMule() {
        super( EntityType.MULE, null );
        this.initEntity();
    }

    private void initEntity() {
        this.setSize( 1.3965f, 1.6f );
        this.addAttribute( Attribute.HEALTH );
        this.setMaxHealth( 30 );
        this.setHealth( 30 );
    }

    @Override
    public void update( long currentTimeMS, float dT ) {
        super.update( currentTimeMS, dT );
    }

}
