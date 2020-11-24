/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.entity.ai;

import io.gomint.math.BlockPosition;
import io.gomint.math.Location;
import io.gomint.math.Vector;
import io.gomint.server.entity.pathfinding.PathfindingEngine;

import java.util.List;
import java.util.concurrent.TimeUnit;

public abstract class AIMovementAndLookingState extends AILookingState {

    private final PathfindingEngine pathfinding;
    private final float movementSpeed;

    private int currentPathNode;
    private List<BlockPosition> path;

    private long lastPointReachedTime;

    protected AIMovementAndLookingState(AIStateMachine machine, float movementSpeed, PathfindingEngine pathfinding) {
        super(machine, pathfinding);

        this.pathfinding = pathfinding;
        this.movementSpeed = movementSpeed;
    }

    @Override
    public void update( long currentTimeMS, float dT ) {
        if ( this.path != null && this.currentPathNode < this.path.size() ) {
            Vector position = this.pathfinding.getTransform().getPosition();
            BlockPosition blockPosition = position.toBlockPosition();

            BlockPosition node = this.path.get( this.currentPathNode );

            Vector direction = node.toVector().add( .5f, 0, .5f ).subtract( position ).normalize().multiply( this.movementSpeed * dT );
            this.pathfinding.getTransform().setMotion( direction.getX(), direction.getY(), direction.getZ() );
            this.look(direction);

            if ( blockPosition.equals( node ) ) {
                this.lastPointReachedTime = currentTimeMS;
                this.currentPathNode++;
            } else if ( currentTimeMS - this.lastPointReachedTime > TimeUnit.SECONDS.toMillis( 5 ) ) {
                // Generating new goal due to entity being stuck in movement loop
                this.pathfinding.setGoal( this.generateGoal() );
                this.path = this.pathfinding.getPath();
                this.currentPathNode = 0;
            }
        } else {
            this.pathfinding.setGoal( this.generateGoal() );
            this.path = this.pathfinding.getPath();
            this.currentPathNode = 0;
            this.lastPointReachedTime = currentTimeMS;
        }
    }

    protected abstract Location generateGoal();

}
