package io.gomint.server.entity.monster;

import io.gomint.server.entity.Attribute;
import io.gomint.server.entity.EntityLiving;
import io.gomint.server.entity.EntityType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.WorldAdapter;

@RegisterInfo( id = 36 )
public class EntityZombiePigman extends EntityLiving implements io.gomint.entity.monster.EntityZombiePigman {

    /**
     * Constructs a new EntityLiving
     *
     * @param world The world in which this entity is in
     */
    public EntityZombiePigman( WorldAdapter world ) {
        super( EntityType.PIG_ZOMBIE, world );
        this.initEntity();
    }

    /**
     * Create new entity zombie pigman for API
     */
    public EntityZombiePigman() {
        super( EntityType.PIG_ZOMBIE, null );
        this.initEntity();
    }

    private void initEntity() {
        this.setSize( 0.6f, 1.95f );
        this.addAttribute( Attribute.HEALTH );
        this.setMaxHealth( 10 );
    }

    @Override
    public void update( long currentTimeMS, float dT ) {
        super.update( currentTimeMS, dT );
    }
}
