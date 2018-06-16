package io.gomint.server.entity.passive;

import io.gomint.server.entity.Attribute;
import io.gomint.server.entity.EntityLiving;
import io.gomint.server.entity.EntityType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.WorldAdapter;

@RegisterInfo( id = 11 )
public class EntityCow extends EntityLiving implements io.gomint.entity.passive.EntityCow {

    /**
     * Constructs a new EntityLiving
     *
     * @param world The world in which this entity is in
     */
    public EntityCow( WorldAdapter world ) {
        super( EntityType.COW, world );
        this.initEntity();
    }

    /**
     * Create new entity cow for API
     */
    public EntityCow() {
        super( EntityType.COW, null );
        this.initEntity();
    }

    private void initEntity() {
        this.setSize( 0.9f, 1.4f );
        this.addAttribute( Attribute.HEALTH );
        this.setMaxHealth( 20 );
        this.setHealth( 20 );
    }

    @Override
    public void update( long currentTimeMS, float dT ) {
        super.update( currentTimeMS, dT );
    }

}
