package io.gomint.server.entity.ai;

import io.gomint.math.Location;
import io.gomint.math.Vector;
import io.gomint.server.entity.pathfinding.PathfindingEngine;
import io.gomint.server.world.WorldAdapter;

import java.util.concurrent.ThreadLocalRandom;

/**
 * AI state that implements the passive movement of idling animals.
 *
 * @author BlackyPaw
 * @version 1.0
 */
public class AIPassiveIdleMovement extends AIMovementAndLookingState {

    private final WorldAdapter world;
    private final PathfindingEngine pathfinding;

    /**
     * Constructs a new AIPassiveIdleMovement that will belong to the given state machine.
     *
     * @param machine     The state machine the AIState being constructed belongs to
     * @param world       The worl the parent entity lives in
     * @param pathfinding The pathfinding engine this entity is using
     */
    public AIPassiveIdleMovement( AIStateMachine machine, WorldAdapter world, PathfindingEngine pathfinding ) {
        super( machine, 2f, pathfinding );
        this.world = world;
        this.pathfinding = pathfinding;
    }

    /**
     * Generates a new random goal within a reasonable distance from the object's
     * current position.
     *
     * @return The generated goal
     */
    protected Location generateGoal() {
        // Generates a new random goal inside a 5 block circle around the entity:
        double t = 2 * Math.PI * ThreadLocalRandom.current().nextDouble();
        double r = 3 + 5 * ThreadLocalRandom.current().nextDouble();
        double x = r * Math.cos( t );
        double z = r * Math.sin( t );

        Vector position = this.pathfinding.getTransform().getPosition().add( (float) x, 0.0F, (float) z );
        return new Location( this.world, position );
    }

}
