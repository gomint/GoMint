package io.gomint.server.entity.monster;

import io.gomint.server.entity.Attribute;
import io.gomint.server.entity.EntityLiving;
import io.gomint.server.entity.EntityType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.WorldAdapter;

@RegisterInfo( id = 105 )
public class EntityVex extends EntityLiving implements io.gomint.entity.monster.EntityVex {

    /**
     * Constructs a new EntityLiving
     *
     * @param world The world in which this entity is in
     */

    public EntityVex( WorldAdapter world ) {
        super( EntityType.VEX, world );
        this.initEntity();
    }

    /**
     * Create new entity stray for API
     */
    public EntityVex() {
        super( EntityType.VEX, null );
        this.initEntity();
    }

    private void initEntity() {
        this.setSize( 0.4f, 0.8f );
        this.addAttribute( Attribute.HEALTH );
        this.setMaxHealth( 14 );
        this.setHealth( 14 );
    }

    @Override
    public void update( long currentTimeMS, float dT ) {
        super.update( currentTimeMS, dT );
    }
}
