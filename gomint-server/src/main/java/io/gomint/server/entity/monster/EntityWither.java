package io.gomint.server.entity.monster;

import io.gomint.server.entity.Attribute;
import io.gomint.server.entity.EntityLiving;
import io.gomint.server.entity.EntityType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.WorldAdapter;

@RegisterInfo( id = 52 )
public class EntityWither extends EntityLiving implements io.gomint.entity.monster.EntityWither {
    /**
     * Constructs a new EntityLiving
     *
     * @param world The world in which this entity is in
     */
    public EntityWither( WorldAdapter world ) {
        super( EntityType.WITHER, world );
        this.initEntity();
    }

    /**
     * Create new entity wither for API
     */
    public EntityWither() {
        super( EntityType.WITHER, null );
        this.initEntity();
    }

    private void initEntity() {
        this.setSize( 0.9f, 3.5f );
        this.addAttribute( Attribute.HEALTH );
        this.setMaxHealth( 600 );
        this.setHealth( 600 );
    }

    @Override
    public void update( long currentTimeMS, float dT ) {
        super.update( currentTimeMS, dT );
    }
}
