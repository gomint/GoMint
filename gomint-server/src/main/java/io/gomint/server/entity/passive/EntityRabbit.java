package io.gomint.server.entity.passive;

import io.gomint.server.entity.Attribute;
import io.gomint.server.entity.EntityLiving;
import io.gomint.server.entity.EntityType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.WorldAdapter;

@RegisterInfo( id = 18 )
public class EntityRabbit extends EntityLiving implements io.gomint.entity.passive.EntityRabbit{

    /**
     * Constructs a new EntityLiving
     *
     * @param world The world in which this entity is in
     */
    public EntityRabbit( WorldAdapter world ) {
        super( EntityType.RABBIT, world );
        this.initEntity();
    }

    /**
     * Create new entity rabbit for API
     */
    public EntityRabbit() {
        super( EntityType.RABBIT, null );
        this.initEntity();
    }

    private void initEntity() {
        this.setSize( 0.4f, 0.5f );
        this.addAttribute( Attribute.HEALTH );
        this.setMaxHealth( 20 );
        this.setHealth( 20 );
    }

    @Override
    public void update( long currentTimeMS, float dT ) {
        super.update( currentTimeMS, dT );
    }
}
