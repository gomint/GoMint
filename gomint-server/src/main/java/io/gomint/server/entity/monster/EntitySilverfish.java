package io.gomint.server.entity.monster;

import io.gomint.server.entity.Attribute;
import io.gomint.server.entity.EntityLiving;
import io.gomint.server.entity.EntityType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.WorldAdapter;

@RegisterInfo( id = 39 )
public class EntitySilverfish extends EntityLiving implements io.gomint.entity.monster.EntitySilverfish {

    /**
     * Constructs a new EntityLiving
     *
     * @param world The world in which this entity is in
     */
    public EntitySilverfish( WorldAdapter world ) {
        super( EntityType.SILVERFISH, world );
        this.initEntity();
    }

    /**
     * Create new entity silverfish for API
     */
    public EntitySilverfish() {
        super( EntityType.SILVERFISH, null );
        this.initEntity();
    }

    private void initEntity() {
        this.setSize( 0.4f, 0.3f );
        this.addAttribute( Attribute.HEALTH );
        this.setMaxHealth( 8 );
        this.setHealth( 8 );
    }

    @Override
    public void update( long currentTimeMS, float dT ) {
        super.update( currentTimeMS, dT );
    }
}
