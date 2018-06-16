package io.gomint.server.entity.passive;

import io.gomint.server.entity.Attribute;
import io.gomint.server.entity.EntityLiving;
import io.gomint.server.entity.EntityType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.WorldAdapter;

@RegisterInfo( id= 14 )
public class EntityWolf extends EntityLiving implements io.gomint.entity.passive.EntityWolf {

    /**
     * Constructs a new EntityLiving
     *
     * @param world The world in which this entity is in
     */
    public EntityWolf( WorldAdapter world ) {
        super( EntityType.WOLF, world );
        this.initEntity();
    }

    /**
     * Create new entity wolf for API
     */
    public EntityWolf() {
        super( EntityType.WOLF, null );
        this.initEntity();
    }

    private void initEntity() {
        this.setSize( 0.6f, 0.85f );
        this.addAttribute( Attribute.HEALTH );
        this.setMaxHealth( 16 );
        this.setHealth( 16 );
    }

    @Override
    public void update( long currentTimeMS, float dT ) {
        super.update( currentTimeMS, dT );
    }
}
