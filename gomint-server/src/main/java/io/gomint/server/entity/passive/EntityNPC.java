package io.gomint.server.entity.passive;

import io.gomint.server.entity.Attribute;
import io.gomint.server.entity.EntityLiving;
import io.gomint.server.entity.EntityType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.WorldAdapter;

/**
 * @author Kaooot
 * @version 1.0
 */
@RegisterInfo( id = 51 )
public class EntityNPC extends EntityLiving implements io.gomint.entity.passive.EntityNPC {

    /**
     * Constructs a new EntityLiving
     *
     * @param world The world in which this entity is in
     */
    public EntityNPC( WorldAdapter world ) {
        super( EntityType.NPC, world );
        this.initEntity();
    }

    /**
     * Create new entity npc for API
     */
    public EntityNPC() {
        super( EntityType.NPC, null );
        this.initEntity();
    }

    private void initEntity() {
        this.setSize( 0.6f, 1.95f );
        this.eyeHeight = 1.62f;
        this.addAttribute( Attribute.HEALTH );
        this.setMaxHealth( 20 );
        this.setHealth( 20 );
    }

    @Override
    public void update( long currentTimeMS, float dT ) {
        super.update( currentTimeMS, dT );
    }
}
