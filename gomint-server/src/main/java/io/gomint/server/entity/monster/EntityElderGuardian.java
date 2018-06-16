package io.gomint.server.entity.monster;

import io.gomint.server.entity.Attribute;
import io.gomint.server.entity.EntityLiving;
import io.gomint.server.entity.EntityType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.WorldAdapter;

@RegisterInfo( id = 50 )
public class EntityElderGuardian extends EntityLiving implements io.gomint.entity.monster.EntityElderGuardian {

    /**
     * Constructs a new EntityLiving
     *
     * @param world The world in which this entity is in
     */
    public EntityElderGuardian( WorldAdapter world ) {
        super( EntityType.ELDER_GUARDIAN, world );
        this.initEntity();
    }

    /**
     * Create new entity elder guardian for API
     */
    public EntityElderGuardian() {
        super( EntityType.ELDER_GUARDIAN, null );
        this.initEntity();
    }

    private void initEntity() {
        this.setSize( 1.9975f, 1.9975f );
        this.addAttribute( Attribute.HEALTH );
        this.setMaxHealth( 80 );
        this.setHealth( 80 );
    }

    @Override
    public void update( long currentTimeMS, float dT ) {
        super.update( currentTimeMS, dT );
    }
}
