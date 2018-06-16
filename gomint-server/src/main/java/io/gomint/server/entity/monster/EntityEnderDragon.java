package io.gomint.server.entity.monster;

import io.gomint.server.entity.Attribute;
import io.gomint.server.entity.EntityLiving;
import io.gomint.server.entity.EntityType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.WorldAdapter;

@RegisterInfo( id = 53  )
public class EntityEnderDragon extends EntityLiving implements io.gomint.entity.monster.EntityEnderDragon {

    /**
     * Constructs a new EntityLiving
     *
     * @param world The world in which this entity is in
     */
    public EntityEnderDragon( WorldAdapter world ) {
        super( EntityType.ENDER_DRAGON, world );
        this.initEntity();
    }

    /**
     * Create new entity ender dragon for API
     */
    public EntityEnderDragon() {
        super( EntityType.ENDER_DRAGON, null );
        this.initEntity();
    }

    private void initEntity() {
        this.setSize( 13.0f, 4.0f );
        this.addAttribute( Attribute.HEALTH );
        this.setMaxHealth( 200 );
        this.setHealth( 200 );
    }

    @Override
    public void update( long currentTimeMS, float dT ) {
        super.update( currentTimeMS, dT );
    }
}
