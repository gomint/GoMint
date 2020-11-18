package io.gomint.server.entity.monster;

import io.gomint.server.entity.Attribute;
import io.gomint.server.entity.EntityLiving;
import io.gomint.server.entity.EntityTags;
import io.gomint.server.entity.EntityType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.WorldAdapter;

import java.util.Set;

@RegisterInfo( sId = "minecraft:vindicator" )
public class EntityVindicator extends EntityLiving implements io.gomint.entity.monster.EntityVindicator {

    /**
     * Constructs a new EntityLiving
     *
     * @param world The world in which this entity is in
     */
    public EntityVindicator( WorldAdapter world ) {
        super( EntityType.VINDICATOR, world );
        this.initEntity();
    }

    /**
     * Create new entity vindicator for API
     */
    public EntityVindicator() {
        super( EntityType.VINDICATOR, null );
        this.initEntity();
    }

    private void initEntity() {
        this.setSize( 0.6f, 1.95f );
        this.addAttribute( Attribute.HEALTH );
        this.setMaxHealth( 24 );
        this.setHealth( 24 );
    }

    @Override
    public void update( long currentTimeMS, float dT ) {
        super.update( currentTimeMS, dT );
    }

    @Override
    public Set<String> getTags() {
        return EntityTags.HOSTILE_MOB;
    }

}
