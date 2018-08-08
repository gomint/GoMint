package io.gomint.server.entity.passive;

import io.gomint.server.entity.Attribute;
import io.gomint.server.entity.EntityLiving;
import io.gomint.server.entity.EntityType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.WorldAdapter;

/**
 * @author Kaooot
 * @version 1.0
 */
@RegisterInfo( id = 56 )
public class EntityAgent extends EntityLiving implements io.gomint.entity.passive.EntityAgent {

    /**
     * Constructs a new EntityLiving
     *
     * @param world The world in which this entity is in
     */
    public EntityAgent( WorldAdapter world ) {
        super( EntityType.AGENT, world );
        this.initEntity();
    }

    /**
     * Create new entity agent for API
     */
    public EntityAgent() {
        super( EntityType.AGENT, null );
        this.initEntity();
    }

    private void initEntity() {
        this.setSize( 0.6f, 1.50f );
        this.addAttribute( Attribute.HEALTH );
        this.setMaxHealth( 2 );
        this.setHealth( 2 );
    }

    @Override
    public void update( long currentTimeMS, float dT ) {
        super.update( currentTimeMS, dT );
    }
}
