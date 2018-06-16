package io.gomint.server.entity.passive;

import io.gomint.server.entity.Attribute;
import io.gomint.server.entity.EntityLiving;
import io.gomint.server.entity.EntityType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.WorldAdapter;

@RegisterInfo( id = 29 )
public class EntityLama extends EntityLiving implements io.gomint.entity.passive.EntityLama {

    /**
     * Constructs a new EntityLiving
     *
     * @param world The world in which this entity is in
     */
    public EntityLama( WorldAdapter world ) {
        super( EntityType.LAMA, world );
        this.initEntity();
    }

    /**
     * Create new entity lama for API
     */
    public EntityLama() {
        super( EntityType.LAMA, null );
        this.initEntity();
    }

    private void initEntity() {
        this.setSize( 0.9f, 1.87f );
        this.addAttribute( Attribute.HEALTH );
        this.setMaxHealth( 30 );
        this.setHealth( 30 );
    }

    @Override
    public void update( long currentTimeMS, float dT ) {
        super.update( currentTimeMS, dT );
    }
}
