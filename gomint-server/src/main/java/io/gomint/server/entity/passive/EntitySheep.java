package io.gomint.server.entity.passive;

import io.gomint.server.entity.Attribute;
import io.gomint.server.entity.EntityLiving;
import io.gomint.server.entity.EntityType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.WorldAdapter;

@RegisterInfo( id = 13 )
public class EntitySheep extends EntityLiving implements io.gomint.entity.passive.EntitySheep {

    /**
     * Constructs a new EntityLiving
     *
     * @param world The world in which this entity is in
     */
    public EntitySheep( WorldAdapter world ) {
        super( EntityType.SHEEP, world );
        this.initEntity();
    }

    /**
     * Create new entity sheep for API
     */
    public EntitySheep() {
        super( EntityType.SHEEP, null );
        this.initEntity();
    }

    private void initEntity() {
        this.setSize( 0.9f, 1.3f );
        this.addAttribute( Attribute.HEALTH );
        this.setMaxHealth( 16 );
        this.setHealth( 16 );
    }

    @Override
    public void update( long currentTimeMS, float dT ) {
        super.update( currentTimeMS, dT );
    }
}
