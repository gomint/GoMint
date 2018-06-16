package io.gomint.server.entity.monster;

import io.gomint.server.entity.Attribute;
import io.gomint.server.entity.EntityLiving;
import io.gomint.server.entity.EntityType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.WorldAdapter;

@RegisterInfo( id = 46 )
public class EntityStray extends EntityLiving implements io.gomint.entity.monster.EntityStray {

    /**
     * Constructs a new EntityLiving
     *
     * @param world The world in which this entity is in
     */
    public EntityStray( WorldAdapter world ) {
        super( EntityType.STRAY, world );
        this.initEntity();
    }

    /**
     * Create new entity stray for API
     */
    public EntityStray() {
        super( EntityType.STRAY, null );
        this.initEntity();
    }

    private void initEntity() {
        this.setSize( 0.6f, 1.99f );
        this.addAttribute( Attribute.HEALTH );
        this.setMaxHealth( 20 );
        this.setHealth( 20 );
    }

    @Override
    public void update( long currentTimeMS, float dT ) {
        super.update( currentTimeMS, dT );
    }
}
