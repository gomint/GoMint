package io.gomint.server.entity.passive;

import io.gomint.server.entity.Attribute;
import io.gomint.server.entity.EntityLiving;
import io.gomint.server.entity.EntityType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.WorldAdapter;

@RegisterInfo( id = 12 )
public class EntityPig extends EntityLiving implements io.gomint.entity.passive.EntityPig {

    /**
     * Constructs a new EntityLiving
     *
     * @param world The world in which this entity is in
     */
    public EntityPig( WorldAdapter world ) {
        super( EntityType.PIG, world );
        this.initEntity();
    }

    /**
     * Create new entity pig for API
     */
    public EntityPig() {
        super( EntityType.PIG, null );
        this.initEntity();
    }

    private void initEntity() {
        this.setSize( 0.9f, 0.9f );
        this.addAttribute( Attribute.HEALTH );
        this.setMaxHealth( 20 );
        this.setHealth( 20 );
    }

    @Override
    public void update( long currentTimeMS, float dT ) {
        super.update( currentTimeMS, dT );
    }
}
