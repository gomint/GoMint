package io.gomint.server.entity.passive;

import io.gomint.server.entity.Attribute;
import io.gomint.server.entity.EntityLiving;
import io.gomint.server.entity.EntityType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.WorldAdapter;

@RegisterInfo( id = 10 )
public class EntityChicken extends EntityLiving implements io.gomint.entity.passive.EntityChicken {

    /**
     * Constructs a new EntityLiving
     *
     * @param world The world in which this entity is in
     */
    public EntityChicken( WorldAdapter world ) {
        super( EntityType.CHICKEN, world );
        this.initEntity();
    }

    /**
     * Create new entity chicken for API
     */
    public EntityChicken() {
        super( EntityType.CHICKEN, null );
        this.initEntity();
    }

    private void initEntity() {
        this.setSize( 0.4f, 0.7f );
        this.addAttribute( Attribute.HEALTH );
        this.setMaxHealth( 8 );
        this.setHealth( 8 );
    }

    @Override
    public void update( long currentTimeMS, float dT ) {
        super.update( currentTimeMS, dT );
    }

}
