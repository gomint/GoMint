package io.gomint.server.entity.monster;

import io.gomint.server.entity.Attribute;
import io.gomint.server.entity.EntityLiving;
import io.gomint.server.entity.EntityType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.WorldAdapter;

@RegisterInfo( id = 44 )
public class EntityZombieVillager extends EntityLiving implements io.gomint.entity.monster.EntityZombieVillager {

    /**
     * Constructs a new EntityLiving
     *
     * @param world The world in which this entity is in
     */
    public EntityZombieVillager( WorldAdapter world ) {
        super( EntityType.ZOMBIE_VILLAGER, world );
        this.initEntity();
    }

    /**
     * Create new entity zombie villager for API
     */
    public EntityZombieVillager() {
        super( EntityType.ZOMBIE_VILLAGER, null );
        this.initEntity();
    }

    private void initEntity() {
        this.setSize( 0.6f, 1.95f );
        this.addAttribute( Attribute.HEALTH );
        this.setMaxHealth( 20 );
        this.setHealth( 20 );
    }

    @Override
    public void update( long currentTimeMS, float dT ) {
        super.update( currentTimeMS, dT );
    }
}
