package io.gomint.server.entity.monster;

import io.gomint.server.entity.Attribute;
import io.gomint.server.entity.EntityLiving;
import io.gomint.server.entity.EntityType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.WorldAdapter;

@RegisterInfo( id = 55 )
public class EntityEndermite extends EntityLiving implements io.gomint.entity.monster.EntityEndermite{

    /**
     * Constructs a new EntityLiving
     *
     * @param world The world in which this entity is in
     */
    public EntityEndermite( WorldAdapter world ) {
        super( EntityType.ENDERMITE, world );
        this.initEntity();
    }

    /**
     * Create new entity blaze for API
     */
    public EntityEndermite() {
        super( EntityType.ENDERMITE, null );
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
