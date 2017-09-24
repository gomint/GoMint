package io.gomint.server.entity;

import io.gomint.entity.Entity;
import io.gomint.server.entity.ai.AIFollowEntity;
import io.gomint.server.world.WorldAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.List;

/**
 * The Entity implementation for cows. Registers AI behaviour and manages other components
 * specific for cows.
 *
 * @author BlackyPaw
 * @version 1.0
 */
public class EntityCow extends EntityLiving {

    private static final Logger LOGGER = LoggerFactory.getLogger( EntityCow.class );

    /**
     * Constructs a new cow entity which will have an AI.
     *
     * @param world The world in which this entity is in
     */
    public EntityCow( WorldAdapter world ) {
        super( EntityType.COW, world );
        this.setSize( 0.9f, 1.3f );
        this.setupAI();
    }

    /**
     * Sets up the AI of the entity by creating the respective AI states, linking them
     * to each other and finally adding them to the AI state machine of the entity's
     * AI behaviour component.
     */
    private void setupAI() {
        /*AIFollowEntity idle = new AIFollowEntity( this.behaviour.getMachine(), this.world, this.pathfinding );
        this.behaviour.getMachine().switchState( idle );*/
    }

    @Override
    public void update( long currentTimeMS, float dT ) {
        super.update( currentTimeMS, dT );

        // Check if we can switch the AI state
        Collection<Entity> nearby = this.world.getNearbyEntities( this.boundingBox.grow( 10, 5, 10 ), this );
        if ( nearby != null ) {
            for ( Entity entity : nearby ) {
                if ( entity instanceof EntityPlayer ) {
                    if ( this.behaviour.getMachine().getActiveState() instanceof AIFollowEntity ) {
                        ( (AIFollowEntity) this.behaviour.getMachine().getActiveState() ).setFollowEntity( (io.gomint.server.entity.Entity) entity );
                    }
                }
            }
        }
    }

}
