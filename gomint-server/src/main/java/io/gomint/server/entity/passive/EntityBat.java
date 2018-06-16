package io.gomint.server.entity.passive;

import io.gomint.server.entity.Attribute;
import io.gomint.server.entity.EntityLiving;
import io.gomint.server.entity.EntityType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.WorldAdapter;

@RegisterInfo( id = 19 )
public class EntityBat extends EntityLiving implements io.gomint.entity.passive.EntityBat {

    /**
     * Constructs a new EntityLiving
     *
     * @param world The world in which this entity is in
     */
    public EntityBat( WorldAdapter world ) {
        super( EntityType.BAT, world );
        this.initEntity();
    }

    /**
     * Create new entity bat for API
     */
    public EntityBat() {
        super( EntityType.BAT, null );
        this.initEntity();
    }

    private void initEntity() {
        this.setSize( 0.5f, 0.9f );
        this.addAttribute( Attribute.HEALTH );
        this.setMaxHealth( 12 );
        this.setHealth( 12 );
    }

    @Override
    public void update( long currentTimeMS, float dT ) {
        super.update( currentTimeMS, dT );
    }



}
