package io.gomint.server.entity.monster;

import io.gomint.server.entity.Attribute;
import io.gomint.server.entity.EntityLiving;
import io.gomint.server.entity.EntityType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.WorldAdapter;

@RegisterInfo( id = 41 )
public class EntityGhast extends EntityLiving implements io.gomint.entity.monster.EntityGhast {

    /**
     * Constructs a new EntityLiving
     *
     * @param world The world in which this entity is in
     */
    public EntityGhast( WorldAdapter world ) {
        super( EntityType.GHAST, world );
        this.initEntity();
    }

    /**
     * Create new entity ghast for API
     */
    public EntityGhast() {
        super( EntityType.GHAST, null );
        this.initEntity();
    }

    private void initEntity() {
        this.setSize( 4.0f, 4.0f );
        this.addAttribute( Attribute.HEALTH );
        this.setMaxHealth( 10);
        this.setHealth( 10 );
    }

    @Override
    public void update( long currentTimeMS, float dT ) {
        super.update( currentTimeMS, dT );
    }
}
