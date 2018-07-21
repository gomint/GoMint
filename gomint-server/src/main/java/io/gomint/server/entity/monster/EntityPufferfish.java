package io.gomint.server.entity.monster;

import io.gomint.server.entity.Attribute;
import io.gomint.server.entity.EntityLiving;
import io.gomint.server.entity.EntityType;
import io.gomint.server.world.WorldAdapter;

public class EntityPufferfish extends EntityLiving implements io.gomint.entity.monster.EntityPufferfish {

    /**
     * Constructs a new EntityLiving
     *
     * @param world The world in which this entity is in
     */
    public EntityPufferfish( WorldAdapter world ) {
        super( EntityType.PUFFERFISH, world );
        this.initEntity();
    }

    /**
     * Create new entity puffer fish for API
     */
    public EntityPufferfish() {
        super( EntityType.PUFFERFISH, null );
        this.initEntity();
    }

    private void initEntity() {
        this.setSize( 0.35f, 0.35f );
        this.addAttribute( Attribute.HEALTH );
        this.setMaxHealth( 3 );
        this.setHealth( 3 );
    }

    @Override
    public void update( long currentTimeMS, float dT ) {
        super.update( currentTimeMS, dT );
    }
}
