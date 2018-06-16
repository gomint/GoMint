package io.gomint.server.entity.monster;

import io.gomint.server.entity.Attribute;
import io.gomint.server.entity.EntityLiving;
import io.gomint.server.entity.EntityType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.WorldAdapter;

@RegisterInfo( id = 49 )
public class EntityGuardian extends EntityLiving implements io.gomint.entity.monster.EntityGuardian {

    /**
     * Constructs a new EntityLiving
     *
     * @param world The world in which this entity is in
     */
    public EntityGuardian( WorldAdapter world ) {
        super( EntityType.GUARDIAN, world );
        this.initEntity();
    }

    /**
     * Create new entity guardian for API
     */
    public EntityGuardian() {
        super( EntityType.GUARDIAN, null );
        this.initEntity();
    }

    private void initEntity() {
        this.setSize( 0.85f, 0.85f );
        this.addAttribute( Attribute.HEALTH );
        this.setMaxHealth( 30 );
        this.setHealth( 30 );
    }

    @Override
    public void update( long currentTimeMS, float dT ) {
        super.update( currentTimeMS, dT );
    }
}
