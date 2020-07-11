/*
 * Copyright (c) 2018 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world.block;

import io.gomint.entity.passive.EntityFallingBlock;
import io.gomint.server.world.UpdateReason;
import io.gomint.world.block.data.Facing;

public abstract class Fallable extends Block {

    @Override
    public long update(UpdateReason updateReason, long currentTimeMS, float dT) {
        if ( updateReason == UpdateReason.NEIGHBOUR_UPDATE ) {
            // Check if the downside block can be replaced
            Block downwards = this.getSide(Facing.DOWN);
            if (downwards.canBeReplaced(null)) {
                // Create entity
                EntityFallingBlock entity = EntityFallingBlock.create();
                entity.setBlock(this);

                // Replace block with air
                this.setBlockType(Air.class);

                // Spawn entity
                entity.spawn(this.location);
            }
        }

        return -1;
    }

}
