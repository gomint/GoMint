package io.gomint.server.entity.monster;

import io.gomint.server.entity.Attribute;
import io.gomint.server.entity.EntityLiving;
import io.gomint.server.entity.EntityType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.WorldAdapter;

@RegisterInfo( id = 28 )
public class EntityPolarBear extends EntityLiving implements io.gomint.entity.monster.EntityPolarBear {

    /**
     * Constructs a new EntityLiving
     *
     * @param world The world in which this entity is in
     */
    public EntityPolarBear( WorldAdapter world ) {
        super( EntityType.POLAR_BEAR, world );
        this.initEntity();
    }

    /**
     * Create new entity polar bear for API
     */
    public EntityPolarBear() {
        super( EntityType.POLAR_BEAR, null );
        this.initEntity();
    }

    private void initEntity() {
        this.setSize( 1.3f, 1.4f );
        this.addAttribute( Attribute.HEALTH );
        this.setMaxHealth( 30 );
        this.setHealth( 30 );
    }

    @Override
    public void update( long currentTimeMS, float dT ) {
        super.update( currentTimeMS, dT );
    }
}
