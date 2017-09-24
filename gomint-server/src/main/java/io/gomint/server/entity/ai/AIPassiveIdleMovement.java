package io.gomint.server.entity.ai;

import io.gomint.math.Location;
import io.gomint.math.Vector;
import io.gomint.server.entity.pathfinding.PathfindingEngine;
import io.gomint.server.util.IntTriple;
import io.gomint.server.world.WorldAdapter;
import io.gomint.util.Numbers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * AI state that implements the passive movement of idling animals.
 *
 * @author BlackyPaw
 * @version 1.0
 */
public class AIPassiveIdleMovement extends AIState {

    private static final Logger LOGGER = LoggerFactory.getLogger( AIPassiveIdleMovement.class );

    private final WorldAdapter world;
    private final PathfindingEngine pathfinding;

    private int currentPathNode;
    private List<IntTriple> path;

    private long lastPointReachedTime;

    /**
     * Constructs a new AIPassiveIdleMovement that will belong to the given state machine.
     *
     * @param machine     The state machine the AIState being constructed belongs to
     * @param world       The worl the parent entity lives in
     * @param pathfinding The pathfinding engine this entity is using
     */
    public AIPassiveIdleMovement( AIStateMachine machine, WorldAdapter world, PathfindingEngine pathfinding ) {
        super( machine );
        this.world = world;
        this.pathfinding = pathfinding;
    }

    @Override
    public void update( long currentTimeMS, float dT ) {
        if ( this.path != null && this.currentPathNode < this.path.size() ) {
            Vector position = this.pathfinding.getTransform().getPosition();

            IntTriple blockPosition = new IntTriple(
                    Numbers.fastFloor( position.getX() ),
                    Numbers.fastFloor( position.getY() ),
                    Numbers.fastFloor( position.getZ() )
            );

            IntTriple node = this.path.get( this.currentPathNode );

            Vector direction = node.toVector().add( .5f, 0, .5f ).subtract( position ).normalize().multiply( 2 * dT );
            this.pathfinding.getTransform().setMotion( direction.getX(), direction.getY(), direction.getZ() );

            if ( blockPosition.equals( node ) ) {
                this.lastPointReachedTime = currentTimeMS;
                this.currentPathNode++;
            } else if ( currentTimeMS - this.lastPointReachedTime > TimeUnit.SECONDS.toMillis( 5 ) ) {
                // Generating new goal due to entity being stuck in movement loop
                this.pathfinding.setGoal( this.generateRandomGoal() );
                this.path = this.pathfinding.getPath();
                this.currentPathNode = 0;
            }
        } else {
            this.pathfinding.setGoal( this.generateRandomGoal() );
            this.path = this.pathfinding.getPath();
            this.currentPathNode = 0;
            this.lastPointReachedTime = currentTimeMS;
        }
    }

    /**
     * Generates a new random goal within a reasonable distance from the object's
     * current position.
     *
     * @return The generated goal
     */
    private Location generateRandomGoal() {
        // Generates a new random goal inside a 5 block circle around the entity:
        double t = 2 * Math.PI * ThreadLocalRandom.current().nextDouble();
        double r = 3 + 5 * ThreadLocalRandom.current().nextDouble();
        double x = r * Math.cos( t );
        double z = r * Math.sin( t );

        Vector position = this.pathfinding.getTransform().getPosition().clone();
        position.add( (float) x, 0.0F, (float) z );

        return new Location( this.world, position );
    }

}
