package io.gomint.server.entity.monster;

import io.gomint.server.entity.Attribute;
import io.gomint.server.entity.EntityLiving;
import io.gomint.server.entity.EntityType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.WorldAdapter;

@RegisterInfo( id = 109 )
public class EntitySalmon extends EntityLiving implements io.gomint.entity.monster.EntitySalmon {

    /**
     * Constructs a new EntityLiving
     *
     * @param world The world in which this entity is in
     */
    public EntitySalmon( WorldAdapter world ) {
        super( EntityType.PUFFERFISH, world );
        this.initEntity();
    }

    /**
     * Create new entity salmon for API
     */
    public EntitySalmon() {
        super( EntityType.PUFFERFISH, null );
        this.initEntity();
    }

    private void initEntity() {
        this.setSize( 0.7f, 0.4f );
        this.addAttribute( Attribute.HEALTH );
        this.setMaxHealth( 3 );
        this.setHealth( 3 );
    }

    @Override
    public void update( long currentTimeMS, float dT ) {
        super.update( currentTimeMS, dT );
    }
}
