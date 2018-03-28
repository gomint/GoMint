package io.gomint.server.entity.passive;

import io.gomint.server.entity.Attribute;
import io.gomint.server.entity.EntityCreature;
import io.gomint.server.entity.EntityType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.WorldAdapter;

@RegisterInfo( id = 61 )
public class EntityArmorStand extends EntityCreature implements io.gomint.entity.passive.EntityArmorStand {

    /**
     * Constructs a new EntityLiving
     *
     * @param world The world in which this entity is in
     */
    public EntityArmorStand( WorldAdapter world ) {
        super( EntityType.ARMOR_STAND, world );
        this.initEntity();
    }

    /**
     * Create new entity chicken for API
     */
    public EntityArmorStand() {
        super( EntityType.ARMOR_STAND, null );
        this.initEntity();
    }

    private void initEntity() {
        this.setSize( 0.5f, 1.975f );
        this.addAttribute( Attribute.HEALTH );
        this.setMaxHealth( 20 );
        this.setHealth( 20 );
    }

    @Override
    public void update( long currentTimeMS, float dT ) {
        super.update( currentTimeMS, dT );
    }

}
