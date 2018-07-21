package io.gomint.server.entity.monster;

import io.gomint.server.entity.Attribute;
import io.gomint.server.entity.EntityLiving;
import io.gomint.server.entity.EntityType;
import io.gomint.server.world.WorldAdapter;

public class EntityCod extends EntityLiving implements io.gomint.entity.monster.EntityCod {

    /**
     * Constructs a new EntityLiving
     *
     * @param world The world in which this entity is in
     */
    public EntityCod( WorldAdapter world ) {
        super( EntityType.PUFFERFISH, world );
        this.initEntity();
    }

    /**
     * Create new entity cod for API
     */
    public EntityCod() {
        super( EntityType.PUFFERFISH, null );
        this.initEntity();
    }

    private void initEntity() {
        this.setSize( 0.5f, 0.3f );
        this.addAttribute( Attribute.HEALTH );
        this.setMaxHealth( 3 );
        this.setHealth( 3 );
    }

    @Override
    public void update( long currentTimeMS, float dT ) {
        super.update( currentTimeMS, dT );
    }
}
