package io.gomint.server.entity.monster;

import io.gomint.server.entity.Attribute;
import io.gomint.server.entity.EntityLiving;
import io.gomint.server.entity.EntityType;
import io.gomint.server.entity.metadata.MetadataContainer;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.WorldAdapter;

/**
 * @author LucGames
 * @version 1.0
 */
@RegisterInfo( id = 37 )
public class EntitySlime extends EntityLiving implements io.gomint.entity.monster.EntitySlime {

    /**
     * Constructs a new EntityLiving
     *
     * @param world The world in which this entity is in
     */
    public EntitySlime( WorldAdapter world ) {
        super( EntityType.SLIME, world );
        this.initEntity();
    }

    /**
     * Create new entity slime for API
     */
    public EntitySlime() {
        super( EntityType.SLIME, null );
        this.initEntity();
    }

    private void initEntity() {
        this.addAttribute( Attribute.HEALTH );

        this.setSizeFactor( 4 );
    }

    @Override
    public void setSizeFactor( int factor ) {
        float newHealth = (float) Math.pow( 2, factor );
        this.setMaxHealth( newHealth );
        this.setHealth( newHealth );
        this.setSize( factor * 0.51f, factor * 0.51f );

        this.metadataContainer.putInt( MetadataContainer.DATA_VARIANT, factor );
    }

    @Override
    public void update( long currentTimeMS, float dT ) {
        super.update( currentTimeMS, dT );
    }
}
