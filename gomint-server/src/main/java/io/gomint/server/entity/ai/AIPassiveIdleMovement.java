package io.gomint.server.entity.ai;

import io.gomint.math.BlockPosition;
import io.gomint.math.Location;
import io.gomint.math.Vector;
import io.gomint.server.entity.pathfinding.PathfindingEngine;
import io.gomint.server.world.WorldAdapter;
import io.gomint.server.world.block.Block;
import io.gomint.world.block.data.Direction;
import io.gomint.world.block.data.Facing;

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
    public AIPassiveIdleMovement(AIStateMachine machine, WorldAdapter world, PathfindingEngine pathfinding) {
        super(machine, 2f, pathfinding);
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
        double x = r * Math.cos(t);
        double z = r * Math.sin(t);

        Vector position = this.pathfinding.getTransform().getPosition().add((float) x, 0.0F, (float) z);
        Block block = this.selectWalkableBlock(position.toBlockPosition(), 10);
        return block.getLocation();
    }

    private Block selectWalkableBlock(BlockPosition pos, int tries) {
        Block block = this.world.getBlockAt(pos);
        if (tries == 0) {
            return block;
        }

        Block down = block.getSide(Facing.DOWN);
        if (!down.isSolid()) {
            // We are not on ground
            return selectWalkableBlock(pos.add(BlockPosition.DOWN), tries - 1);
        }

        if (!block.canPassThrough()) {
            for (Direction value : Direction.values()) {
                Block other = block;
                for (int i = 0; i < 3; i++) {
                    other = other.getSide(value);
                    if (other.canPassThrough()) {
                        return other;
                    }
                }
            }
        }

        return block;
    }

}
