/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.entity.ai;

import io.gomint.math.MathUtils;
import io.gomint.math.Vector;
import io.gomint.server.entity.pathfinding.PathfindingEngine;

public class AILookingState extends AIState {

    private final PathfindingEngine pathfinding;

    /**
     * Constructs a new AIState that will belong to the given state machine.
     *
     * @param machine The state machine the AIState being constructed belongs to
     */
    protected AILookingState(AIStateMachine machine, PathfindingEngine pathfinding) {
        super(machine);
        this.pathfinding = pathfinding;
    }

    public void look(Vector direction) {
        double horizontalDistance = MathUtils.sqrt(direction.getX() * direction.getX() + direction.getZ() * direction.getZ());
        float yaw = (float) ((Math.atan2(direction.getZ(), direction.getX()) * (180D / Math.PI)) - 90.0F);
        float pitch = (float) -(Math.atan2(direction.getY(), horizontalDistance) * (180D / Math.PI));

        this.pathfinding.getTransform().setHeadYaw(this.updateRotation(this.pathfinding.getTransform().getHeadYaw(), yaw, 10));
        this.pathfinding.getTransform().setPitch(this.updateRotation(this.pathfinding.getTransform().getPitch(), pitch, 40));
    }

    private float updateRotation(float current, float add, float max) {
        float f = MathUtils.wrapDegrees(add - current);

        if (f > max) {
            f = max;
        }

        if (f < -max) {
            f = -max;
        }

        return current + f;
    }

}
