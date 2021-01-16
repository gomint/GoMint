package io.gomint.server.entity.ai;

import io.gomint.math.BlockPosition;
import io.gomint.math.MathUtils;
import io.gomint.math.Vector;
import io.gomint.server.entity.Entity;
import io.gomint.server.entity.pathfinding.PathfindingEngine;
import io.gomint.server.world.WorldAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author geNAZt
 * @version 1.0
 */
public class AIFollowEntity extends AIState {

    private static final Logger LOGGER = LoggerFactory.getLogger( AIFollowEntity.class );

    private final WorldAdapter world;
    private final PathfindingEngine pathfinding;

    private int currentPathNode;
    private List<BlockPosition> path;

    private Entity<?> followEntity;

    /**
     * Constructs a new AIState that will belong to the given state machine.
     *
     * @param machine     The state machine the AIState being constructed belongs to
     * @param world       The world the parent entity lives in
     * @param pathfinding The pathfinding engine this entity is using
     */
    public AIFollowEntity( AIStateMachine machine, WorldAdapter world, PathfindingEngine pathfinding ) {
        super( machine );
        this.world = world;
        this.pathfinding = pathfinding;
    }

    /**
     * Set a new follow entity target
     *
     * @param entity the new entity to follow
     */
    public AIFollowEntity followEntity(Entity<?> entity ) {
        this.followEntity = entity;
        return this;
    }

    @Override
    protected void update( long currentTimeMS, float dT ) {
        // No target to follow
        if ( this.followEntity == null ) {
            return;
        }

        if ( this.path != null && this.currentPathNode < this.path.size() ) {
            Vector position = this.pathfinding.transform().position();

            BlockPosition blockPosition = new BlockPosition(
                MathUtils.fastFloor( position.x() ),
                MathUtils.fastFloor( position.y() ),
                MathUtils.fastFloor( position.z() )
            );

            BlockPosition node = this.path.get( this.currentPathNode );

            // Check if we need to jump
            boolean jump = node.y() > position.y();

            Vector direction = node.toVector().add( .5f, 0, .5f ).subtract( position ).normalize().multiply( 4.31f * dT ); // 4.31 is the normal player movement speed per second
            if ( jump ) {
                direction.y( 1f ); // Default jump height
            }

            this.pathfinding.transform().motion( direction.x(), direction.y(), direction.z() );

            LOGGER.debug( "Current pos: {}; Needed: {}; Direction: {}", position, node, direction );

            if ( blockPosition.equals( node ) ) {
                this.currentPathNode++;
            }
        } else if ( this.followEntity.onGround() ) {
            LOGGER.debug( "Current follow position: {}", this.followEntity.location() );

            this.path = this.pathfinding
                .goal( this.followEntity.location() )
                .path();

            this.currentPathNode = 0;
        }
    }

}
