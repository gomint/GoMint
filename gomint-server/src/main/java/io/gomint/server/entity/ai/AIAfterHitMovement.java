/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.entity.ai;

import io.gomint.math.Location;
import io.gomint.math.Vector;
import io.gomint.server.entity.pathfinding.PathfindingEngine;
import io.gomint.server.world.WorldAdapter;

public class AIAfterHitMovement extends AIMovementAndLookingState {

    private final Vector direction;
    private final PathfindingEngine pathfinding;
    private final WorldAdapter world;

    private AIState before;
    private boolean fired;

    /**
     * Constructs a new AIState that will belong to the given state machine.
     *
     * @param machine     The state machine the AIState being constructed belongs to
     * @param world       which the entity is in
     * @param direction   in which the entity should run
     * @param pathfinding
     */
    public AIAfterHitMovement(AIStateMachine machine, WorldAdapter world, Vector direction, PathfindingEngine pathfinding) {
        super(machine, 6, pathfinding);
        this.direction = direction;
        this.pathfinding = pathfinding;
        this.world = world;
    }

    @Override
    protected void onEnter(AIState oldState) {
        this.before = oldState;
    }

    @Override
    protected Location generateGoal() {
        if (this.fired) {
            // Switch back to state
            this.switchState(this.before);
        }

        this.fired = true;

        Vector direction = this.direction.normalize().multiply(10);
        direction.y(0);

        Vector position = this.pathfinding.transform().position().add(direction);
        return new Location(this.world, position);
    }

}
